package com.ekaryagin.milkcrm.entity.employee;

import com.ekaryagin.milkcrm.entity.Shop;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@DiscriminatorValue("SE")
public class Seller extends User {

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Shop> shops;

    private boolean strictRule;

    public Set<Shop> getShops() {
        return shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    public boolean isStrictRule() {
        return strictRule;
    }

    public void setStrictRule(boolean strictRule) {
        this.strictRule = strictRule;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "shops=" + shops +
                ", strictRule=" + strictRule +
                ", id=" + id +
                ", nic='" + username + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", active=" + active +
                '}';
    }
}
