package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Role;

import java.util.List;

public class SellerDTO {

    private long id;
    private String username;
    private String fio;
    private String email;
    private long phoneNumber;
    private Role role;
    private boolean active;
    private RegionDTO region;
    private List<ShopDTO> shops;
    private boolean strictRule;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public List<ShopDTO> getShops() {
        return shops;
    }

    public void setShops(List<ShopDTO> shops) {
        this.shops = shops;
    }

    public boolean isStrictRule() {
        return strictRule;
    }

    public void setStrictRule(boolean strictRule) {
        this.strictRule = strictRule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SellerDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role +
                ", active=" + active +
                ", region=" + region +
                ", shops=" + shops +
                ", strictRule=" + strictRule +
                ", password='" + password + '\'' +
                '}';
    }
}
