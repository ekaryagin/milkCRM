package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.repository.ProductGroupRepo;
import com.ekaryagin.milkcrm.repository.RegionRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private final RegionRepo regionRepo;
    private final ProductGroupRepo productGroupRepo;


    public RegionService(RegionRepo regionRepo, ProductGroupRepo productGroupRepo) {
        this.regionRepo = regionRepo;
        this.productGroupRepo = productGroupRepo;
    }

    public Region readRegionById(long id){
        return regionRepo.findById(id);
    }

    public List<Region> readRegions() {

        return regionRepo.findAll();
    }

    public List<Region> readRegionsByProductGroup(long id){

        return regionRepo.findAllByProducts(productGroupRepo.findById(id));
    }

    public int addRegion (Region region){
        if (regionRepo.findById(region.getId()) != null || regionRepo.findByTitle(region.getTitle()) != null) {
            return 1;
        }
        regionRepo.save(region);
        return 0;
    }

    public int saveRegion (Region region){
        if (regionRepo.findById(region.getId()) == null){
            return 7;
        } else if (!regionRepo.findById(region.getId()).getTitle().equals(region.getTitle())
                && regionRepo.findByTitle(region.getTitle()) != null) {
            return 1;
        }
        regionRepo.save(region);
        return 0;
    }

    public boolean deleteRegion(long id){
        Region region = regionRepo.findById(id);

        if (region == null || !region.getShops().isEmpty()){
           return false;
        }

        regionRepo.delete(region);
        return true;
    }
}
