package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepo extends JpaRepository<Region, Long> {

    Region findById(long id);

    List<Region> findAllByProducts(ProductGroup productGroup);

    Region findByTitle(String title);
}
