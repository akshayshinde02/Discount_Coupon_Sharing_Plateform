package com.coupons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coupons.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(String email);
}
