package com.ekaryagin.milkcrm.entity.employee;

import com.ekaryagin.milkcrm.entity.Region;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="usr")
@Inheritance(
        strategy = InheritanceType.SINGLE_TABLE
)
@DiscriminatorColumn(name = "BD_TYPE")
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    protected String username;
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> grantedRole = new ArrayList<>();
        grantedRole.add(role);
        return grantedRole;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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
