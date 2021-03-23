package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.products.AbnormalAmount;
import com.ekaryagin.milkcrm.entity.products.Kvant;
import com.ekaryagin.milkcrm.entity.products.Price;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;

import java.util.List;

public class ProductDTOext {

    private long id;
    private boolean active;
    private ProductGroup group;
    private Manager author;
    private String article;
    private String title;
    private String measureUnit;
    private double mainPrice;
    private double mainKvant;
    private double mainAbnormalAmount;

    private List<Price> price;
    private List<Kvant> kvant;
    private List<AbnormalAmount> abnormalAmount;

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

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    public List<Kvant> getKvant() {
        return kvant;
    }

    public void setKvant(List<Kvant> kvant) {
        this.kvant = kvant;
    }

    public List<AbnormalAmount> getAbnormalAmount() {
        return abnormalAmount;
    }

    public void setAbnormalAmount(List<AbnormalAmount> abnormalAmount) {
        this.abnormalAmount = abnormalAmount;
    }
}
