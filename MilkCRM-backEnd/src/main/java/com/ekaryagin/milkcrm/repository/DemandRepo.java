package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.Demand;
import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DemandRepo extends JpaRepository<Demand, Long> {

    ArrayList<Demand> findAllByShopAndGroupAndDoneIsTrue(Shop shop, ProductGroup group);

    ArrayList<Demand> findAllByShop(Shop shop);

    ArrayList<Demand> findAllByAuthor(Seller seller);

    ArrayList<Demand> findAllByApprover(Manager manager);

    ArrayList<Demand> findAllByRegion(Region region);

    ArrayList<Demand> findAllByRegionAndApprovedIsFalse(Region region);

    Demand findDemandByAuthorAndShopAndDoneIsFalse(Seller seller, Shop shop);
}
