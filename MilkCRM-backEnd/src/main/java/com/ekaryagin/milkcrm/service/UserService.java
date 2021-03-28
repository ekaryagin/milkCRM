package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final ShopService shopService;
    private final DemandService demandService;
    private final AdService adService;
    private final ProductService productService;
    private final ProductGroupService productGroupService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepo userRepo, ShopService shopService, DemandService demandService, AdService adService, ProductService productService, ProductGroupService productGroupService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.shopService = shopService;
        this.demandService = demandService;
        this.adService = adService;
        this.productService = productService;
        this.productGroupService = productGroupService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Seller readSeller(long id){
        Seller seller = null;
        if (userRepo.findById(id) != null
                && (userRepo.findById(id).getRole().equals(Role.RETAIL_SELLER)
                || userRepo.findById(id).getRole().equals(Role.DEALER))){
            seller = (Seller) userRepo.findById(id);
        }
        return seller;
    }

    public List<Seller> readAllSellers(){
        List<User> users = userRepo.findAllByRole(Role.RETAIL_SELLER);
        users.addAll(userRepo.findAllByRole(Role.DEALER));

        return getSellers(users);
    }

    public List<Seller> readAllRetailSellers(){
        return getSellers(userRepo.findAllByRole(Role.RETAIL_SELLER));
    }

    public List<Seller> readAllDealers(){
        return getSellers(userRepo.findAllByRole(Role.DEALER));
    }

    public List<Seller> readAllSellersFromRegion(Region region){
        ArrayList<Seller> sellers = new ArrayList<>(getSellers(userRepo.findAllByRegionsAndRole(region, Role.RETAIL_SELLER)));
        sellers.addAll(getSellers(userRepo.findAllByRegionsAndRole(region, Role.DEALER)));
        return sellers;
    }

    public List<Seller> readAllRetailSellersFromRegion(Region region){
        return getSellers(userRepo.findAllByRegionsAndRole(region, Role.RETAIL_SELLER));
    }

    public List<Seller> readAllDealersFromRegion(Region region){
        return getSellers(userRepo.findAllByRegionsAndRole(region, Role.DEALER));
    }

    public List<Manager> readAllMangersFromRegion(Region region) {
        return getManagers(userRepo.findAllByRegionsAndRole(region, Role.MANAGER));
    }

    public List<Seller> getSellers (List<User> users){
        List<Seller> sellers = new ArrayList<>();
        for (User user: users) {
            sellers.add((Seller) user);
        }
        return sellers;
    }

    public Set<Seller> readAllSellersFromShop(long id){
        Shop shop = shopService.getShopById(id);
        return shop != null
                ? shop.getSellers()
                : null;
    }

    public int addUser(User user){

        if (userRepo.findById(user.getId()) != null || userRepo.findByUsername(user.getUsername()) != null) {
            return 1;
        } else if(user.getPassword().length() < 8){
            return 2;
        } else if (user.getFio().length() < 6){
            return 3;
        } else if (!checkUserPhone(user)){
            return 4;
        } else if (!checkUserEmail(user)) {
            return 5;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return 0;
    }

    public int saveUser (User user){

        if (readSeller(user.getId()) == null){
            return 7;
        }else if (!userRepo.findById(user.getId()).getUsername().equals(user.getUsername())
                && userRepo.findByUsername(user.getUsername()) != null){
            return 1;
        } else if (!checkUserPassword(user)) {
            return 2;
        } else if (!checkUserFio(user)) {
            return 3;
        } else if (!checkUserPhone(user)){
            return 4;
        } else if ((user.getRole()==Role.ADMIN || user.getRole()==Role.MANAGER) && !checkUserEmail(user)) {
            return 5;
        } else if ((user.getRole() == Role.DEALER || user.getRole() == Role.RETAIL_SELLER)
                && user.getRegions() == null) {
            return 6;
        }

        userRepo.save(user);
        return 0;
    }

    public boolean deleteSeller(long id){
        if (demandService.getAllDemandByAuthor((Seller)userRepo.findById(id)).isEmpty()){
            userRepo.delete(userRepo.findById(id));
            return true;
        }
        return false;
    }

    public boolean checkUserPassword(User user){
        return user.getPassword().length() > 8;
    }

    public boolean checkUserFio (User user){
        return user.getFio().length() >= 8;
    }

    public boolean checkUserEmail (User user){
        Pattern emailRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailRegex.matcher(user.getEmail());

        return matcher.find();
    }

    public boolean checkUserPhone (User user){
        return 10_000_000_000L >= user.getPhoneNumber() && user.getPhoneNumber() >= 9_000_000_000L;
    }

    public List<Manager> readAllAdmins() {
        List<User> users = userRepo.findAllByRole(Role.ADMIN);
        return getManagers(users);
    }

    public List<Manager> readAllManagers() {
        List<User> users = userRepo.findAllByRole(Role.MANAGER);
        return getManagers(users);
    }

    public List<Manager> getManagers (List<User> users){
        List<Manager> manager = new ArrayList<>();
        for (User user: users) {
            manager.add((Manager) user);
        }
        return manager;
    }

    public Manager readManager(long id) {
        Manager manager = null;
        if (userRepo.findById(id) != null
                && (userRepo.findById(id).getRole().equals(Role.MANAGER)
                || userRepo.findById(id).getRole().equals(Role.ADMIN))){
            manager = (Manager) userRepo.findById(id);
        }
        return manager;
    }

    public boolean deleteManager(long id) {
        Manager manager = (Manager)userRepo.findById(id);

        if (!demandService.getAllDemandByManager(manager).isEmpty()
                || !adService.getAllAd(manager).isEmpty()
                || !productService.getProductByAuthor(manager).isEmpty()
                || !productGroupService.getProductGroupByUser(manager).isEmpty()){
            return false;
        }
        userRepo.delete(userRepo.findById(id));
        return true;
    }

    public User getUser(String userName) {
        if (userRepo.findByUsername(userName).getRole() == Role.ADMIN
                || userRepo.findByUsername(userName).getRole() == Role.MANAGER){
            return userRepo.findByUsername(userName);
        }
        return userRepo.findByUsername(userName);
    }
}
