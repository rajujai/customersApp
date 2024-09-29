package com.customers.app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    CUSTOMER_READ("customer_read"),
    CUSTOMER_UPDATE("customer_update"),
    CUSTOMER_CREATE("customer_create"),
    CUSTOMER_DELETE("customer_delete");

    private final String permission;
}
