package com.ekaryagin.milkcrm.entity.employee;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, MANAGER, RETAIL_SELLER, DEALER;

    @Override
    public String getAuthority() {
        return name();
    }
}
