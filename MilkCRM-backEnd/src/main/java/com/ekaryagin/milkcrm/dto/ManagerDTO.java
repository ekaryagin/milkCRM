package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Role;

import java.util.List;

public class ManagerDTO {

    private long id;
    private String nic;
    private String fio;
    private String email;
    private long phoneNumber;
    private Role role;
    private boolean active;
    private String password;
    private List<ProductGroupDTO> productGroups;
    private List<RegionDTO> regions;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ProductGroupDTO> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroupDTO> productGroups) {
        this.productGroups = productGroups;
    }

    public List<RegionDTO> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDTO> regions) {
        this.regions = regions;
    }
}
