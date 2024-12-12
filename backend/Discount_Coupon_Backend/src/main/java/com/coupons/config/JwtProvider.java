package com.coupons.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.coupons.exceptions.InvalidTokenException;
import com.coupons.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtProvider {

    private JwtConstant jwtConstant;

    private SecretKey key;

    @Autowired
    public JwtProvider(JwtConstant jwtConstant) {
        this.jwtConstant = jwtConstant;
    }

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtConstant.getSecretKey().getBytes());
    }

    public String generateToken(Authentication auth) {

        // Fetching email and authorities from database
        String email = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("authorities", authoritiesString);

        // claims.put("authorities", authorities.stream()
        // .map(GrantedAuthority::getAuthority)
        // .collect(Collectors.toList()));

        @SuppressWarnings("deprecation")
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConstant.getExpiration()))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();

        return jwt;

    }

    public String generateRefreshToken(Authentication auth) {

        String email = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        @SuppressWarnings("deprecation")
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConstant.getRefreshTokenExpiration()))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();

        return jwt;

    }

    public String getEmailFromToken(String jwt) {

        jwt = jwt.substring(7);
        @SuppressWarnings("deprecation")
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));

    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {

        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : collection) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }

    public boolean isTokenValid(String token, User user) {

        if (token != null) {
            token = token.substring(7);

            try {

                Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();

                String email = String.valueOf(claims.get("email"));
                // String authoraties = String.valueOf(claims.get("authorities",String.class));
                List<String> authoritiesList = claims.get("authorities", List.class);
                String authorities = String.join(", ", authoritiesList);

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                return true;

            } catch (Exception e) {
                throw new InvalidTokenException("Invalid or Expired Token");
            }
        }
        return false;
    }

}
