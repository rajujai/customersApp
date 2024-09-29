package com.customers.app.utils;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretKeyGenerator {
    @Test
    public void generateSecretKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String secretKey = DatatypeConverter.printHexBinary(key.getEncoded());
        System.out.printf("\nGenerated secret key: [%s] \n", secretKey);
    }
}
