package com.ekaryagin.milkcrm.entity.products;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="product_group")
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String vendor;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<Manager> owner;

    private String templatePath;
    private int articleColumn;
    private int countColumn;
    private String sheetName;

    private int addressRow;
    private int addressCell;
    private int dateRow;
    private int dateCell;

    private String titleForFile;

}
