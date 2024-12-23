package com.coupons.services;

import java.util.List;

import com.coupons.exceptions.CouponException;
import com.coupons.models.Rating;
import com.coupons.models.User;
import com.coupons.request.RatingRequest;

public interface RatingService {
    
    public Rating createRating(RatingRequest req, User user) throws CouponException;

    public List<Rating> getCouponsRating(Long couponId);
}
