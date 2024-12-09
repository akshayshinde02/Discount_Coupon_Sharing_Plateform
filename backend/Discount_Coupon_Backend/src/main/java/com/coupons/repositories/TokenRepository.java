package com.coupons.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupons.models.UserToken;

public interface TokenRepository extends JpaRepository<UserToken,Long>{
    
    Optional<UserToken> findByToken(String token);
}
