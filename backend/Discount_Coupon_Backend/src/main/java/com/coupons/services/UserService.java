package com.coupons.services;

import com.coupons.exceptions.UserException;
import com.coupons.models.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    
}
