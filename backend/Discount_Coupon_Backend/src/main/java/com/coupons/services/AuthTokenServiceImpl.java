package com.coupons.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.coupons.config.JwtProvider;
import com.coupons.exceptions.TokenException;
import com.coupons.models.User;
import com.coupons.models.UserToken;
import com.coupons.repositories.TokenRepository;
import com.coupons.repositories.UserRepository;
import com.coupons.response.AuthResponse;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void revokeAllUserTokens(User user) throws TokenException{

        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        
        if (validUserTokens.isEmpty()) {
            throw new TokenException("No valid tokens found!!");
        }
        
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }

    public AuthResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException,TokenException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new TokenException("Missing or invalid Authorization header");
        }
        final String refreshToken;
        final String userEmail;
        
        refreshToken = authHeader;
        userEmail = jwtProvider.getEmailFromToken(refreshToken);
        
        if (userEmail == null) {
            throw new TokenException("Email not found for Token");
        }

        User user = userRepository.findByEmail(userEmail);
        if (user == null || !jwtProvider.isTokenValid(refreshToken, user)) {
            throw new TokenException("Invalid or expired refresh token");
        }
        
        List<UserToken> userTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (userEmail != null) {
            if (jwtProvider.isTokenValid(refreshToken, user)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(), null, user.getRole().getAuthorities());
                        
                var accessToken = jwtProvider.generateToken(authentication);
                revokeAllUserTokens(user);

                AuthResponse authResponse = new AuthResponse();
                authResponse.setJwt(accessToken);
                authResponse.setRefreshJwt(refreshToken);
                return authResponse;
            } else {
                throw new TokenException("Invalid or expired refresh token.");
            }
        }else {
            throw new TokenException("Unable to extract user details from token or session logout.");
            
        } 
    }

    // private void saveUserToken(User user, String jwtToken) {
    // var token = UserToken.builder()
    // .user(user)
    // .token(jwtToken)
    // .tokenType(TokenType.BEARER)
    // .expired(false)
    // .revoked(false)
    // .build();
    // tokenRepository.save(token);
    // }

}
