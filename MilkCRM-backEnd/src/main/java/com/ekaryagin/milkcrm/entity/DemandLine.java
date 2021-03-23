package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.products.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="demand_line")
public class DemandLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Demand demand;

    double count;

}
