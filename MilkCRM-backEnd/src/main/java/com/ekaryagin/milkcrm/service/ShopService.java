package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.repository.ShopRepo;
import com.ekaryagin.milkcrm.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final UserRepo userRepo;
    private final ShopRepo shopRepo;
    private final DemandService demandService;


    public ShopService(UserRepo userRepo, ShopRepo shopRepo, DemandService demandService) {
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
        this.demandService = demandService;
    }

    public boolean saveChangesShop (Shop shop){
        try{
            shopRepo.save(shop);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public List<Shop> getShopsForUser(User user, Region region){
        if (user.getRole() == Role.RETAIL_SELLER || user.getRole() == Role.DEALER) {
            Seller seller = (Seller) userRepo.findByNic(user.getNic());
            return (List<Shop>) seller.getShops();
        } else {
            return shopRepo.findAllByRegion(region);
        }
    }

    public List<Shop> getShopsByLegalEntity (String legalEntity){
        return shopRepo.findAllByLegalEntity(legalEntity);
    }

    public Shop getShopById(long id){
        return shopRepo.findById(id);
    }

    public List<Shop> getShops() {
        return shopRepo.findAll();
    }

    public List<Shop> getShopsByRegions(Region region) {
        return shopRepo.findAllByRegion(region);
    }

    public List<Shop> getShopsByProductGroup(ProductGroup productGroup) {
        return shopRepo.findAllByProductGroups(productGroup);
    }

    public int addShop (Shop shop){

        if (shopRepo.findById(shop.getId()) != null || shopRepo.findShopByAddress(shop.getAddress()) != null) {
            return 1;
        } else if(shop.getRegion() == null){
            return 2;
        } else if (shop.getAddress() == null || shop.getDescription() == null || shop.getLegalEntity() == null){
            return 3;
        } else if (shop.getSellers() == null){
            return 4;
        } else if (shop.getProductGroups() == null) {
            return 5;
        }

        shopRepo.save(shop);
        return 0;
    }

    public int saveShop(Shop shop) {

        if (shopRepo.findById(shop.getId()) == null){
            return 7;
        }else if (shopRepo.findById(shop.getId()) != null || shopRepo.findShopByAddress(shop.getAddress()) != null) {
            return 1;
        } else if(shop.getRegion() == null){
            return 2;
        } else if (shop.getAddress() == null || shop.getDescription() == null || shop.getLegalEntity() == null){
            return 3;
        } else if (shop.getSellers() == null){
            return 4;
        } else if (shop.getProductGroups() == null) {
            return 5;
        }

        shopRepo.save(shop);
        return 0;
    }

    public boolean deleteShop (long id){
        Shop shop = shopRepo.findById(id);

        if (!demandService.getAllDemandByShop(shop).isEmpty()){
            return false;
        }
        shopRepo.delete(shop);
        return true;
    }
}
