package com.example.spring.apringbootsecuritykimseheak.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TokenProviderTest {
    private TokenProvider tokenProvider;
    @Test
    void generate_secret_key(){
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encoded = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("b :: " + encoded);
    }
}