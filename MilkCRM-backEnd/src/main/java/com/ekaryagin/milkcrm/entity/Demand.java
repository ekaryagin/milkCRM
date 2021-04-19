package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.employee.Seller;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seller getAuthor() {
        return author;
    }

    public void setAuthor(Seller author) {
        this.author = author;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Manager getApprover() {
        return approver;
    }

    public void setApprover(Manager approver) {
        this.approver = approver;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public ProductGroup getGroup() {
        return group;
    }

    public void setGroup(ProductGroup group) {
        this.group = group;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Timestamp processingDate) {
        this.processingDate = processingDate;
    }

    public void setListing(Set<DemandLine> listing) {
        this.listing = listing;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

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
