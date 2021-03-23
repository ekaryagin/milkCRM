package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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

}
