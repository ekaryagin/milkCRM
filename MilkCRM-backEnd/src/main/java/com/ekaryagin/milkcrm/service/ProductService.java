package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductGroupService productGroupService;


    public ProductService(ProductRepo productRepo, ProductGroupService productGroupService) {
        this.productRepo = productRepo;
        this.productGroupService = productGroupService;
    }

    public Optional<Product> getProduct(long id){
        return productRepo.findById(id);
    }

    public List<Product> getProductsForUser(User user, ProductGroup productGroup){

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

    public List<Product> getProductByProductGroup (ProductGroup productGroup){
        return productRepo.findAllByGroup(productGroup);
    }

    public boolean addNewProduct (Product product) {
        if (productRepo.findProductByArticle(product.getArticle()).isPresent()){
            return false;
        }
        try {
            productRepo.save(product);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean saveProduct(Product product){
        if (productRepo.findProductByArticle(product.getArticle()).isPresent()
                && productRepo.findProductByArticle(product.getArticle()).get().getId() != product.getId()){
            return false;
        }
        try {
            productRepo.save(product);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public Map<String, Boolean> addNewProductsFromExcel (ProductGroup group, Manager author, MultipartFile excelFile){
        Map<String, Boolean> answer = new HashMap<>();
        ArrayList<Product> products;
        try {
            products = new ArrayList<>(ExcelParser.getProductsFromExcel(group, author, excelFile));
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
