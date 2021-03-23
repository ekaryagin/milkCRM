package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AdDTO {

    private long id;
    private Manager author;
    private String title;
    private String text;
    private Timestamp creationDate;

}
