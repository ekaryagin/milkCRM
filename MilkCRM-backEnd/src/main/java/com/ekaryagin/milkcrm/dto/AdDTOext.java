package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
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


}
