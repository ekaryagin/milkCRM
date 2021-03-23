package com.ekaryagin.milkcrm.dto;

public class ProductDTO {

    private long id;
    private String article;
    private String title;
    private String measureUnit;
    private double price;
    private double kvant;
    private double abnormalAmount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getKvant() {
        return kvant;
    }

    public void setKvant(double kvant) {
        this.kvant = kvant;
    }

    public double getAbnormalAmount() {
        return abnormalAmount;
    }

    public void setAbnormalAmount(double abnormalAmount) {
        this.abnormalAmount = abnormalAmount;
    }
}
