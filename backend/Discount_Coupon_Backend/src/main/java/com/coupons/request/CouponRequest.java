package com.coupons.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {

    public String couponName;
    public String couponDescription;
    public Integer price;
    public LocalDate expirationDate;
    public String couponCode;
    public String categoryName;
    public Boolean termsAccepted;
    
}
