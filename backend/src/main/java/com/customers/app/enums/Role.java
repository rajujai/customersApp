package com.customers.app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.customers.app.enums.Permission.CUSTOMER_CREATE;
import static com.customers.app.enums.Permission.CUSTOMER_DELETE;
import static com.customers.app.enums.Permission.CUSTOMER_READ;
import static com.customers.app.enums.Permission.CUSTOMER_UPDATE;


@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(
            CUSTOMER_READ,
            CUSTOMER_CREATE
    )),
    ADMIN(Set.of(
            CUSTOMER_READ,
            CUSTOMER_CREATE,
            CUSTOMER_UPDATE,
            CUSTOMER_DELETE
    ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}