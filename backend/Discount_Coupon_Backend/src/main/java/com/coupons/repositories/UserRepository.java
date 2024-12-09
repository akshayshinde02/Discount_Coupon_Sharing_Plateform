package com.coupons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupons.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
    public User findByEmail(String email);
}
