package com.coupons.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coupons.config.JwtProvider;
import com.coupons.exceptions.UserException;
import com.coupons.models.User;
import com.coupons.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public User findUserById(Long userId) throws UserException {
       
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id "+userId);
    }


    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new UserException("user not exist with email "+email);
        }
        return user;

    }


    
}
