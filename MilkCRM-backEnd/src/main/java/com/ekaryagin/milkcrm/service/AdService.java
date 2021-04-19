package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.Ad;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.repository.AdRepo;
import com.ekaryagin.milkcrm.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AdService {

    private final AdRepo adRepo;
    private final UserRepo userRepo;

    public AdService(AdRepo adRepo, UserRepo userRepo) {
        this.adRepo = adRepo;
        this.userRepo = userRepo;
    }

    public List<Ad> getAllAdForUser(User user){
        List<Ad> ad = new ArrayList<>();
        if (user.getRole() == Role.ADMIN) {
            ad.addAll(adRepo.findAll());
        } else if (user.getRole() == Role.RETAIL_SELLER){
            Seller seller = (Seller) userRepo.findByUsername(user.getUsername());
            for (Region region: seller.getRegions()) {
                ad.addAll(adRepo.findAllByRegionsAndForSellerTrueAndDisplayStartDateLessThanEqualAndDisplayEndDateGreaterThanEqual
                        (region, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
            }
        } else if (user.getRole() == Role.DEALER){
            Seller seller = (Seller) userRepo.findByUsername(user.getUsername());
            for (Region region: seller.getRegions()) {
                ad.addAll(adRepo.findAllByRegionsAndForDealerTrueAndDisplayStartDateLessThanEqualAndDisplayEndDateGreaterThanEqual
                        (region, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
            }
        } else if (user.getRole() == Role.MANAGER){
            Manager manager = (Manager) userRepo.findByUsername(user.getUsername());
            Set<Ad> set = new HashSet<>();
            for (Region region: manager.getRegions()) {
                set.addAll(adRepo.findAllByRegionsAndDisplayStartDateLessThanEqualAndDisplayEndDateGreaterThanEqual
                        (region, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
            }
            set.addAll(adRepo.findAllByAuthor(manager));
            ad.addAll(set);
        }

        ad.sort(Ad.startDateComparator);
        return ad;
    }

    public List<Ad> getAllAd(){
        return adRepo.findAll();
    }

    public Optional<Ad> getAdById(long id){
        return adRepo.findById(id);
    }

    public List<Ad> getAllForRegion(Region region){
        return adRepo.findAllByRegions(region);
    }

    public List<Ad> getAdsByAuthor(Manager manager){
        return adRepo.findAllByAuthor(manager);
    }

    public boolean saveAd (Ad ad){
        try {
            adRepo.save(ad);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean deleteAd(long id) {
        try {
            adRepo.delete(adRepo.getOne(id));
        } catch (Exception ex){
            return false;
        }
        return true;
    }
}
