package com.coupons.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coupons.models.OtpVerification;

public interface OtpRepository extends JpaRepository<OtpVerification,Long>{
    
    OtpVerification findByEmailAndIsUsedFalse(String email);

    Optional<OtpVerification> findByEmailIgnoreCase(String email);

    
}
