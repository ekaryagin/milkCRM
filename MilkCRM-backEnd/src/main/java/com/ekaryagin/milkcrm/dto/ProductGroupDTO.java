package com.ekaryagin.milkcrm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductGroupDTO {

    private long id;
    private String title;
    private String vendor;
    private List<ManagerDTO> owner;

}
