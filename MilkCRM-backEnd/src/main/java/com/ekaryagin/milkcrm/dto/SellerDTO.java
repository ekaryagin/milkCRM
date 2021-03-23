package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerDTO {

    private long id;
    private String nic;
    private String fio;
    private String email;
    private long phoneNumber;
    private Role role;
    private boolean active;
    private RegionDTO region;
    private List<ShopDTO> shops;
    private boolean strictRule;
    private String password;

    @Override
    public String toString() {
        return "SellerDTO{" +
                "id=" + id +
                ", nic='" + nic + '\'' +
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
