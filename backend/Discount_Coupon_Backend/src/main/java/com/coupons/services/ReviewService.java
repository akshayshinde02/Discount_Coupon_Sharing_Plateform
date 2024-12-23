package com.coupons.services;

import java.util.List;

import com.coupons.exceptions.CouponException;
import com.coupons.models.Review;
import com.coupons.models.User;
import com.coupons.request.ReviewRequest;

public interface ReviewService {
    
    public Review createReview(ReviewRequest req,User user) throws CouponException;
	
	public List<Review> getAllReview(Long couponId);
}
