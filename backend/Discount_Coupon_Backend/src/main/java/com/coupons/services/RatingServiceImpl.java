package com.coupons.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coupons.exceptions.CouponException;
import com.coupons.models.Coupon;
import com.coupons.models.Rating;
import com.coupons.models.User;
import com.coupons.repositories.CouponRepository;
import com.coupons.repositories.RatingRepository;
import com.coupons.request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService{

    private RatingRepository ratingRepository;
	private CouponService couponService;
    private CouponRepository couponRepository;
	
	public RatingServiceImpl(RatingRepository ratingRepository,CouponService couponService,CouponRepository couponRepository) {
		this.ratingRepository=ratingRepository;
		this.couponService=couponService;
        this.couponRepository = couponRepository;
	}

    @Override
    public Rating createRating(RatingRequest req, User user) throws CouponException {
     
        Coupon coupon = couponService.getSingleCoupon(req.getCouponId());

        Rating rating = new Rating();
        rating.setCoupon(coupon);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        couponRepository.save(coupon);

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getCouponsRating(Long couponId) {
       return ratingRepository.getAllCouponsRating(couponId);
    }
    
}
