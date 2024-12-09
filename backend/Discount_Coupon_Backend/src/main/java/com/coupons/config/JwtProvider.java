package com.coupons.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
    
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());

    public String generateToken(Authentication auth){
        @SuppressWarnings("deprecation")
        String jwt = Jwts.builder().setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + 86400000))
        .claim("email", auth.getName())
        .signWith(key)
        .compact();

        return jwt;

    }

    public String getEmailFromToken(String jwt){

        jwt = jwt.substring(7);
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));

    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection){

        Set<String> auths = new HashSet<>();

        for(GrantedAuthority authority: collection){
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }
}
