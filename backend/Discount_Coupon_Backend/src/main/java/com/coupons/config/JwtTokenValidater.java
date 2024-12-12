package com.coupons.config;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coupons.exceptions.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class JwtTokenValidater extends OncePerRequestFilter{

private JwtConstant jwtConstant;
private static SecretKey key;

@Autowired
public JwtTokenValidater(JwtConstant jwtConstant) {
    this.jwtConstant = jwtConstant;
}


    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtConstant.getSecretKey().getBytes());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
            String jwt = request.getHeader(JwtConstant.JWT_HEADER);
            
            if(jwt!=null){
                jwt = jwt.substring(7);
                

                try {
                    
                    // SecretKey key = Keys.hmacShaKeyFor(jwtConstant.getSecretKey().getBytes());
                    Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                    String email = String.valueOf(claims.get("email"));
                    String authoraties = String.valueOf(claims.get("authorities"));

                    List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authoraties);

                    Authentication authentication = new UsernamePasswordAuthenticationToken(email,null,auths);

                    SecurityContextHolder.getContext().setAuthentication(authentication);


                } catch (Exception e) {
                     throw new InvalidTokenException("Invalid or Expired Token");
                }
            }

            filterChain.doFilter(request, response);
    }
    
}
