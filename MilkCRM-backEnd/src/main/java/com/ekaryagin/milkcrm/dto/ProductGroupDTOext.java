package com.ekaryagin.milkcrm.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductGroupDTOext {

    private long id;
    private String title;
    private String vendor;
    private List<ManagerDTO> owner;

    private int articleColumn;
    private int countColumn;
    private String sheetName;
    private int addressRow;
    private int addressCell;
    private int dateRow;
    private int dateCell;
    private String titleForFile;
}
