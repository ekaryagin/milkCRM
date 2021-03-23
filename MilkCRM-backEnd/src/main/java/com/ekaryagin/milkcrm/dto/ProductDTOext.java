package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.products.AbnormalAmount;
import com.ekaryagin.milkcrm.entity.products.Kvant;
import com.ekaryagin.milkcrm.entity.products.Price;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
