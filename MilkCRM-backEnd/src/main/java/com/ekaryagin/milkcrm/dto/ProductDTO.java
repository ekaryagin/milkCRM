package com.ekaryagin.milkcrm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private long id;
    private String article;
    private String title;
    private String measureUnit;
    private double price;
    private double kvant;
    private double abnormalAmount;
}
