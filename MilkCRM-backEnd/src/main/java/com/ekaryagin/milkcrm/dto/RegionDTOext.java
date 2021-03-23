package com.ekaryagin.milkcrm.dto;

import java.util.List;

public class RegionDTOext {

    private long id;
    private String title;
    private boolean active;
    private List<ProductGroupDTO> productGroups;
    private List<ShopDTO> shops;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ProductGroupDTO> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroupDTO> productGroups) {
        this.productGroups = productGroups;
    }

    public List<ShopDTO> getShops() {
        return shops;
    }

    public void setShops(List<ShopDTO> shops) {
        this.shops = shops;
    }
}
