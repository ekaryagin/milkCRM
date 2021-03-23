package com.ekaryagin.milkcrm.entity.employee;

import com.ekaryagin.milkcrm.entity.Shop;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity
@DiscriminatorValue("SE")
public class Seller extends User {

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Shop> shops;

    private boolean strictRule;

    @Override
    public String toString() {
        return "Seller{" +
                "shops=" + shops +
                ", strictRule=" + strictRule +
                ", id=" + id +
                ", nic='" + nic + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }
}
