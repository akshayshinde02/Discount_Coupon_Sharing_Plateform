package com.coupons.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.coupons.models.Coupon;
import com.coupons.models.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private LocalDateTime orderDateTime;

    private BigDecimal totalPrice;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    private List<Coupon> orderCoupons;
    
}
