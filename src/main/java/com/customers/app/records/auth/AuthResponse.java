package com.customers.app.records.auth;

import lombok.Builder;

@Builder
public record AuthResponse(String accessToken, String refreshToken) {
}
