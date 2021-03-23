package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.Region;

import java.util.List;

public class ShopDTO {

    private long id;
    private boolean active;
    private Region region;
    private String address;
    private String description;
    private String legalEntity;
    private List<ProductGroupDTO> productGroups;
    private List<SellerDTO> sellers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public List<ProductGroupDTO> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroupDTO> productGroups) {
        this.productGroups = productGroups;
    }

    public List<SellerDTO> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerDTO> sellers) {
        this.sellers = sellers;
    }
}
