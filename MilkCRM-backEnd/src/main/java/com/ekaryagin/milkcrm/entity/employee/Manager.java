package com.ekaryagin.milkcrm.entity.employee;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MA")
public class Manager extends User {

}
