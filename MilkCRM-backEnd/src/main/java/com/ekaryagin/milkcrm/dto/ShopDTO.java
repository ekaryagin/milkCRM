package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.Region;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopDTO {

    private long id;
    private boolean active;
    private Region region;
    private String address;
    private String description;
    private String legalEntity;
    private List<ProductGroupDTO> productGroups;
    private List<SellerDTO> sellers;

}
