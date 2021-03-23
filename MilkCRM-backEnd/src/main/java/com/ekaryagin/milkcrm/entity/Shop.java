package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean active;

    @ManyToOne
    private Region region;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Seller> sellers;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<ProductGroup> productGroups;

    private String address;
    private String description;
    private String legalEntity;

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

    public Set<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(Set<Seller> sellers) {
        this.sellers = sellers;
    }

    public Set<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(Set<ProductGroup> productGroups) {
        this.productGroups = productGroups;
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

    @Override
    public String toString() {
        return  description + " " + address;
    }
}
