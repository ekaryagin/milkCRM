package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.Demand;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.repository.DemandRepo;
import com.ekaryagin.milkcrm.repository.ShopRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemandService {

    public final DemandRepo demandRepo;
    public final ShopRepo shopRepo;

    public DemandService(DemandRepo demandRepo, ShopRepo shopRepo) {
        this.demandRepo = demandRepo;
        this.shopRepo = shopRepo;
    }

    public Demand getLastDemand(Shop shop, ProductGroup productGroup){
        List<Demand> demands = demandRepo.findAllByShopAndGroupAndDoneIsTrue(shop, productGroup);
        demands.sort(Demand.processingDateComparator);
        return demands.get(demands.size() - 1);

    }

    public List<Demand> getAllDemandByShop(Shop shop){
        ArrayList<Demand> demands = demandRepo.findAllByShop(shop);
        demands.sort(Demand.processingDateComparator);
        return demands;
    }

    public List<Demand> getAllDemandByAuthor(Seller seller){
        ArrayList<Demand> demands = demandRepo.findAllByAuthor(seller);
        demands.sort(Demand.processingDateComparator);
        return demands;
    }

    public List<Demand> getAllDemandByManager(Manager manager){
        ArrayList<Demand> demands = demandRepo.findAllByApprover(manager);
        demands.sort(Demand.processingDateComparator);
        return demands;
    }

    public List<Demand> getAllDemandByLegalEntity (String legalEntity){
        ArrayList<Shop> shops = shopRepo.findAllByLegalEntity(legalEntity);
        ArrayList<Demand> demands = new ArrayList<>();
        for (Shop shop: shops) {
            demands.addAll(demandRepo.findAllByShop(shop));
        }
        demands.sort(Demand.processingDateComparator);
        return demands;
    }

    public Demand createNewDemand (Seller seller, Shop shop){
        Demand demand = demandRepo.findDemandByAuthorAndShopAndDoneIsFalse(seller, shop);
        if (demand != null){
            return demand;
        }
        return new Demand();
    }

    public boolean sendDemandToManager (Demand demand) {
        try {
            demand.setDone(true);
            demandRepo.save(demand);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public List<Demand> getNewDemands (Region region){
        ArrayList<Demand> demands = demandRepo.findAllByRegionAndApprovedIsFalse(region);
        demands.sort(Demand.processingDateComparator);
        return demands;
    }

    public List<Demand> getAllDemandsInRegion(Region region){
        ArrayList<Demand> demands = demandRepo.findAllByRegion(region);
        demands.sort(Demand.processingDateComparator);
        return demands;
    }

    public void exportDemand(Demand demand){
        try {
            ExcelParser.exportDemand(demand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
