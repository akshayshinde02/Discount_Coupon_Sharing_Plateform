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

@RequiredArgsConstructor
public enum UserRole {
    
    USER(Collections.emptySet()),
    ADMIN(
        Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_DELETE,
            ADMIN_UPDATE
        )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){

        var authorities = getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }
}
