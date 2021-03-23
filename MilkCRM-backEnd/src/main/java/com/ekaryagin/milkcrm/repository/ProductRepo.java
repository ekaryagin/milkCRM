package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByGroup(ProductGroup productGroup);

    List<Product> findAllByGroupAndActiveTrue(ProductGroup productGroup);

    Product findProductByArticle(String article);

    List<Product> findAllByAuthor (Manager manager);

}
