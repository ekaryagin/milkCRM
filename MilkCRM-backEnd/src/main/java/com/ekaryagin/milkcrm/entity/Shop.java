package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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

    @Override
    public String toString() {
        return  description + " " + address;
    }
}
