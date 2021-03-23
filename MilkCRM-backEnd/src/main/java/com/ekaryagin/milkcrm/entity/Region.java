package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.products.ProductGroup;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private boolean active;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<Shop> shops;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<ProductGroup> products;

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

    public Set<Shop> getShops() {
        return shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    public Set<ProductGroup> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductGroup> products) {
        this.products = products;
    }
}
