package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductGroupRepo extends JpaRepository<ProductGroup, Long> {

    ProductGroup findByTitle(String title);

    ProductGroup findById(long id);

    List<ProductGroup> findAllByOwner (Manager manager);

}
