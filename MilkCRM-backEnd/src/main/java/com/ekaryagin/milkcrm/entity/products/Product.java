package com.ekaryagin.milkcrm.entity.products;

import com.ekaryagin.milkcrm.entity.employee.Manager;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean active;

    @ManyToOne
    private ProductGroup group;

    @ManyToOne
    private Manager author;

    private String article;
    private String title;
    private String measureUnit;
    private double mainPrice;
    private double mainKvant;
    private double mainAbnormalAmount;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<Price> price;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<Kvant> kvant;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<AbnormalAmount> abnormalAmount;

    public Product() {
    }

    public Product(ProductGroup group, Manager author, String article, String title, String measureUnit, double mainPrice,
                   double mainKvant, double mainAbnormalAmount) {
        this.group = group;
        this.author = author;
        this.article = article;
        this.title = title;
        this.measureUnit = measureUnit;
        this.mainPrice = mainPrice;
        this.mainKvant = mainKvant;
        this.mainAbnormalAmount = mainAbnormalAmount;
        active = false;
    }

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

    public ProductGroup getGroup() {
        return group;
    }

    public void setGroup(ProductGroup group) {
        this.group = group;
    }

    public Manager getAuthor() {
        return author;
    }

    public void setAuthor(Manager author) {
        this.author = author;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public double getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(double mainPrice) {
        this.mainPrice = mainPrice;
    }

    public double getMainKvant() {
        return mainKvant;
    }

    public void setMainKvant(double mainKvant) {
        this.mainKvant = mainKvant;
    }

    public double getMainAbnormalAmount() {
        return mainAbnormalAmount;
    }

    public void setMainAbnormalAmount(double mainAbnormalAmount) {
        this.mainAbnormalAmount = mainAbnormalAmount;
    }

    public Set<Price> getPrice() {
        return price;
    }

    public void setPrice(Set<Price> price) {
        this.price = price;
    }

    public Set<Kvant> getKvant() {
        return kvant;
    }

    public void setKvant(Set<Kvant> kvant) {
        this.kvant = kvant;
    }

    public Set<AbnormalAmount> getAbnormalAmount() {
        return abnormalAmount;
    }

    public void setAbnormalAmount(Set<AbnormalAmount> abnormalAmount) {
        this.abnormalAmount = abnormalAmount;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", article='" + article + '\'' +
                ", title='" + title + '\'' +
                ", measureUnit='" + measureUnit + '\'' +
//                ", prices=" + prices +
                ", kvant=" + mainKvant +
                ", abnormalAmount=" + mainAbnormalAmount +
                '}';
    }

}
