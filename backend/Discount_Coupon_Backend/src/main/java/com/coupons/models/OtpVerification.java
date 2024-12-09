package com.coupons.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otpHash;
    private String email;
    private LocalDateTime exTimestamp;
    private Boolean isUsed = false;

    private LocalDateTime createdAt;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(exTimestamp);
    }
    
}
