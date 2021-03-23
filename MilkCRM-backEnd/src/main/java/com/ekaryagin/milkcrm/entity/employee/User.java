package com.ekaryagin.milkcrm.entity.employee;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="usr")
@Inheritance(
        strategy = InheritanceType.SINGLE_TABLE
)
@DiscriminatorColumn(name = "BD_TYPE")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    protected String nic;
    protected String fio;
    protected String email;
    protected long phoneNumber;

    protected String password;

    @Enumerated(EnumType.STRING)
    protected Role role;

    protected boolean active;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Region> regions;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ProductGroup> productGroups;

}
