package com.ekaryagin.milkcrm.entity.products;

import com.ekaryagin.milkcrm.entity.Region;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Region region;

    @ManyToOne
    private Product product;

    private double value;
}
