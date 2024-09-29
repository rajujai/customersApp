package com.customers.app.records.auth;

public record SignupRequest(String firstname, String lastname, String email, String password, String role) {
}
