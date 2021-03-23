package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.employee.Manager;

import java.sql.Timestamp;
import java.util.Set;

public class AdDTOext {

    private long id;
    private Manager author;
    private String title;
    private String text;
    private Timestamp creationDate;
    private Timestamp displayStartDate;
    private Timestamp displayEndDate;
    private Set<Region> regions;
    private boolean forSeller;
    private boolean forDealer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Manager getAuthor() {
        return author;
    }

    public void setAuthor(Manager author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getDisplayStartDate() {
        return displayStartDate;
    }

    public void setDisplayStartDate(Timestamp displayStartDate) {
        this.displayStartDate = displayStartDate;
    }

    public Timestamp getDisplayEndDate() {
        return displayEndDate;
    }

    public void setDisplayEndDate(Timestamp displayEndDate) {
        this.displayEndDate = displayEndDate;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public boolean isForSeller() {
        return forSeller;
    }

    public void setForSeller(boolean forSeller) {
        this.forSeller = forSeller;
    }

    public boolean isForDealer() {
        return forDealer;
    }

    public void setForDealer(boolean forDealer) {
        this.forDealer = forDealer;
    }
}
