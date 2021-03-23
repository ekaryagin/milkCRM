package com.ekaryagin.milkcrm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegionDTOext {

    private long id;
    private String title;
    private boolean active;
    private List<ProductGroupDTO> productGroups;
    private List<ShopDTO> shops;

}
