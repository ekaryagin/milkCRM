package com.ekaryagin.milkcrm.entity.employee;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;

import javax.persistence.*;
import java.util.Set;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public Set<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(Set<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }
}
