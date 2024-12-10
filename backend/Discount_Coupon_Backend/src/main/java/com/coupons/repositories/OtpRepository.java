package com.coupons.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coupons.models.OtpVerification;

public interface OtpRepository extends JpaRepository<OtpVerification,Long>{
    
   @Query("SELECT o FROM OtpVerification o WHERE o.email = :email AND o.isUsed = false ORDER BY o.createdAt DESC") 
    OtpVerification findByEmailAndIsUsedFalse(String email);

    Optional<OtpVerification> findByEmailIgnoreCase(String email);

    
}
