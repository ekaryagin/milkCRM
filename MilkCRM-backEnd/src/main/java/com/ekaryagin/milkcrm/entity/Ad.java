package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.employee.Manager;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name="ad")
public class Ad{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Manager author;

    private String title;
    private String text;
    private Timestamp creationDate;
    private Timestamp displayStartDate;
    private Timestamp displayEndDate;

    @OneToMany(fetch= FetchType.EAGER)
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

    public static final Comparator<Ad> startDateComparator =
            Comparator.comparing(Ad::getDisplayStartDate);

}
