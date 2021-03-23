package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name="demand")
public class Demand{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Seller author;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private Manager approver;

    @ManyToOne
    private Region region;

    @ManyToOne
    private ProductGroup group;

    private Timestamp creationDate;
    private Timestamp processingDate;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<DemandLine> listing;

    private boolean done;
    private boolean approved;

    private boolean attention;
    private String comment;
    private float totalCost;

    public Map<Product, Double> getListing() {
        Map<Product, Double> order = new HashMap<>();
        for (DemandLine demandLine : listing) {
            order.put(demandLine.getProduct(), demandLine.getCount());
        }
        return order;
    }

    public static final Comparator<Demand> processingDateComparator =
            Comparator.comparing(Demand::getProcessingDate);

}
