package com.coupons.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coupons.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("Select r from Review r where r.coupon.id=:couponId")
	public List<Review> getAllCouponsReview(@Param("couponId") Long couponId);
}

