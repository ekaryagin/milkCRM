package com.ekaryagin.milkcrm.repository;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByGroup(ProductGroup productGroup);

    List<Product> findAllByGroupAndActiveTrue(ProductGroup productGroup);

    Optional<Product> findProductByArticle(String article);

    Optional<Product> findById(long id);

    List<Product> findAllByAuthor (Manager manager);

}
