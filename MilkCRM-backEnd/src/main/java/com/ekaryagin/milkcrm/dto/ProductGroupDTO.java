package com.ekaryagin.milkcrm.dto;

import java.util.List;

public class ProductGroupDTO {

    private long id;
    private String title;
    private String vendor;
    private List<ManagerDTO> owner;

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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<ManagerDTO> getOwner() {
        return owner;
    }

    public void setOwner(List<ManagerDTO> owner) {
        this.owner = owner;
    }
}
