package com.coupons.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.exceptions.UserException;
import com.coupons.models.User;
import com.coupons.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(user,HttpStatus.ACCEPTED);

    }
    
}
