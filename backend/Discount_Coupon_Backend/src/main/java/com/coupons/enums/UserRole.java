package com.coupons.enums;


import static com.coupons.enums.Permission.ADMIN_CREATE;
import static com.coupons.enums.Permission.ADMIN_DELETE;
import static com.coupons.enums.Permission.ADMIN_READ;
import static com.coupons.enums.Permission.ADMIN_UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum UserRole {
    
    USER,
    ADMIN;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
