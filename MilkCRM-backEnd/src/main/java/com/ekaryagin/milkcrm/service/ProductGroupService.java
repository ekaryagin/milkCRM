package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.employee.Role;
import com.ekaryagin.milkcrm.entity.employee.User;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import com.ekaryagin.milkcrm.repository.ProductGroupRepo;
import com.ekaryagin.milkcrm.repository.ShopRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroupService {

    private final ProductGroupRepo productGroupRepo;
    private final ShopRepo shopRepo;

    public ProductGroupService(ProductGroupRepo productGroupRepo, ShopRepo shopRepo) {
        this.productGroupRepo = productGroupRepo;
        this.shopRepo = shopRepo;
    }

    public List<ProductGroup> getProductGroups(){
        return productGroupRepo.findAll();
    }

    public List<ProductGroup> getProductGroupByUser(User user){
        if (user.getRole() != Role.ADMIN) {
            return (List<ProductGroup>) user.getProductGroups();
        } else {
            return productGroupRepo.findAll();
        }
    }

    public ProductGroup getProductGroup(long id){
        return productGroupRepo.findById(id);
    }

    public List<ProductGroup> getProductGroupByShop(long id){
        return (List<ProductGroup>) shopRepo.findById(id).getProductGroups();
    }

    public int addNewGroup (ProductGroup group){

        if (productGroupRepo.findById(group.getId()) != null
                || productGroupRepo.findByTitle(group.getTitle()) != null) {
            return 1;
        } else if(group.getArticleColumn() < 0
                || group.getCountColumn() < 0
                || group.getArticleColumn() == group.getCountColumn()){
            return 2;
        } else if (group.getAddressCell() < 0 || group.getAddressRow() < 0){
            return 3;
        } else if (group.getDateCell() < 0 || group.getDateRow() < 0){
            return 4;
        } else if (group.getDateCell() == group.getAddressCell()
                && group.getDateRow() == group.getAddressRow()) {
            return 5;
        }

        productGroupRepo.save(group);
        return 0;
    }

    public int saveGroup (ProductGroup group){
        if (productGroupRepo.findById(group.getId()) == null){
            return 7;
        } else if (!productGroupRepo.findById(group.getId()).getTitle().equals(group.getTitle())
                || productGroupRepo.findByTitle(group.getTitle()) != null) {
            return 1;
        } else if(group.getArticleColumn() < 0
                || group.getCountColumn() < 0
                || group.getArticleColumn() == group.getCountColumn()){
            return 2;
        } else if (group.getAddressCell() < 0 || group.getAddressRow() < 0){
            return 3;
        } else if (group.getDateCell() < 0 || group.getDateRow() < 0){
            return 4;
        } else if (group.getDateCell() == group.getAddressCell()
                && group.getDateRow() == group.getAddressRow()) {
            return 5;
        }

        productGroupRepo.save(group);
        return 0;
    }

    public boolean deleteProductGroup(long id) {
        ProductGroup productGroup = productGroupRepo.findById(id);
        if (productGroup == null){
            return false;
        }

        productGroupRepo.delete(productGroup);
        return true;
    }
}
