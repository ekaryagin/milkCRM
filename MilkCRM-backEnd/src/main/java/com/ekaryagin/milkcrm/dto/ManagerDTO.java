package com.ekaryagin.milkcrm.dto;

import com.ekaryagin.milkcrm.entity.employee.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
