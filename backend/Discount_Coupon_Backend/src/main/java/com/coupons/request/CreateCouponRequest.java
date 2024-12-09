package com.coupons.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponRequest {
    
    private String couponName;
    private String couponDescription;
    private int discountValue;
    private LocalDate ExpirationDate;
    private String couponCode;
    private boolean termsAndCondition;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
