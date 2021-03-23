package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.products.Product;

import javax.persistence.*;

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

    private double count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
