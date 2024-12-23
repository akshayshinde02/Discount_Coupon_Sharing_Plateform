package com.coupons.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coupons.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    

    @Query("Select r from Rating r where r.coupon.id=:couponId")
    public List<Rating> getAllCouponsRating(@Param("couponId") Long couponId);
}
