package com.customers.app.exceptions;

import java.io.IOException;

public class EntityAlreadyExistsException extends IOException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
