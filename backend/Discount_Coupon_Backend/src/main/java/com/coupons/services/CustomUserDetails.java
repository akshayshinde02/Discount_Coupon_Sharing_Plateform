package com.coupons.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coupons.models.User;
import com.coupons.repositories.UserRepository;

@Service
public class CustomUserDetails implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Loading the User From Database and checking Creadiantials
        User user = userRepository.findByEmail(username);
        if(user == null) {
			throw new UsernameNotFoundException("Invalid email or user does not exist");
    
		}

        // Fetching the role and authorities from database
        List<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }


    
}
