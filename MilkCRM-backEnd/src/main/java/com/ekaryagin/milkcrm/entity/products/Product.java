package com.ekaryagin.milkcrm.entity.products;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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
