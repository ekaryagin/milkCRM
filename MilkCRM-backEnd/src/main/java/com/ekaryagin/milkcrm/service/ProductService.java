package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductGroupService productGroupService;


    public ProductService(ProductRepo productRepo, ProductGroupService productGroupService) {
        this.productRepo = productRepo;
        this.productGroupService = productGroupService;
    }

    public List<Product> getProduct(User user, ProductGroup productGroup){

        if (user.getRole() != Role.ADMIN) {
            if (!productGroupService.getProductGroupByUser(user).contains(productGroup)){
                return new ArrayList<>();
            }
            return productRepo.findAllByGroupAndActiveTrue(productGroup);
        } else {
            return productRepo.findAllByGroup(productGroup);
        }
    }

    public List<Product> getProductByAuthor (Manager manager){
        return productRepo.findAllByAuthor(manager);
    }

    public boolean addNewProduct (Product product){
        if (productRepo.findProductByArticle(product.getArticle()) != null){
            return false;
        }
        try {
            productRepo.save(product);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public Map<String, Boolean> addNewProductsFromExcel (ProductGroup group, Manager author, String excelFilePath){
        Map<String, Boolean> answer = new HashMap<>();
        ArrayList<Product> products;
        try {
            products = new ArrayList<>(ExcelParser.getProductsFromExcel(group, author, excelFilePath));
        } catch (IOException e) {
            answer.put("File not found or damage", true);
            return answer;
        }
        for (Product product: products) {
            answer.put(product.getArticle(), addNewProduct(product));
        }
        return answer;
    }

}
