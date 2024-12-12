package com.coupons.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtConstant {

    @Value("${application.security.jwt.secret-key}")
    public String secretKey;

    @Value("${application.security.jwt.expiration}")
    public long expiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    public long refreshTokenExpiration;

    public static final String JWT_HEADER="Authorization";

    private SecretKey key;

@PostConstruct
public void init() {
    key = Keys.hmacShaKeyFor(secretKey.getBytes());
}

public SecretKey getKey() {
    return key;
}


    
    
    public String getSecretKey() {
        return secretKey;
    }

    public long getExpiration() {
        return expiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}
