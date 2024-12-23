package com.coupons.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coupons.exceptions.CouponException;
import com.coupons.models.Coupon;
import com.coupons.models.Review;
import com.coupons.models.User;
import com.coupons.repositories.CouponRepository;
import com.coupons.repositories.ReviewRepository;
import com.coupons.request.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
	private CouponService couponService;
	private CouponRepository couponRepository;
	
	public ReviewServiceImpl(ReviewRepository reviewRepository,CouponService couponService,CouponRepository couponRepository) {
		this.reviewRepository=reviewRepository;
		this.couponService=couponService;
		this.couponRepository=couponRepository;
	}

    @Override
    public Review createReview(ReviewRequest req, User user) throws CouponException {
        
        Coupon coupon = couponService.getSingleCoupon(req.getCouponId());

        Review review=new Review();
		review.setUser(user);
		review.setCoupon(coupon);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		couponRepository.save(coupon);
		return reviewRepository.save(review);

    }

    @Override
    public List<Review> getAllReview(Long couponId) {

        return reviewRepository.getAllCouponsReview(couponId);
    }
    
}
