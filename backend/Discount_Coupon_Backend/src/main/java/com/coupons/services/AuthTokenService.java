package com.coupons.services;

import com.coupons.exceptions.TokenException;
import com.coupons.models.User;

public interface AuthTokenService {

    void revokeAllUserTokens(User user) throws TokenException;
    
}
