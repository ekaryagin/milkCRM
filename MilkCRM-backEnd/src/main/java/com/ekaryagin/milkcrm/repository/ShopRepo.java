package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.Shop;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ShopRepo extends JpaRepository<Shop, Long> {

    ArrayList<Shop> findAllByRegion (Region region);

    ArrayList<Shop> findAllByLegalEntity (String legalEntity);

    ArrayList<Shop> findAllByProductGroups (ProductGroup productGroup);

    Shop findById(long id);

    Shop findShopByAddress (String address);
}
