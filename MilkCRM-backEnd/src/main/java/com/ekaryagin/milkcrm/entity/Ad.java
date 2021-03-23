package com.ekaryagin.milkcrm.entity;

import com.ekaryagin.milkcrm.entity.employee.Manager;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="ad")
public class Ad{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Manager author;

    private String title;
    private String text;
    private Timestamp creationDate;
    private Timestamp displayStartDate;
    private Timestamp displayEndDate;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<Region> regions;

    private boolean forSeller;
    private boolean forDealer;

    public static final Comparator<Ad> startDateComparator =
            Comparator.comparing(Ad::getDisplayStartDate);


}
