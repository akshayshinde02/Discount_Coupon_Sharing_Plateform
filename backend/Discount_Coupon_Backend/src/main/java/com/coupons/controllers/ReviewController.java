package com.coupons.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Review;
import com.coupons.models.User;
import com.coupons.request.ReviewRequest;
import com.coupons.services.ReviewService;
import com.coupons.services.UserService;


@RestController
@RequestMapping("/user/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private UserService userService;
	
	public ReviewController(ReviewService reviewService,UserService userService) {
		this.reviewService=reviewService;
		this.userService=userService;
		// TODO Auto-generated constructor stub
	}
	@PostMapping("/create")
	public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req,@RequestHeader("Authorization") String jwt) throws UserException, CouponException{

		User user=userService.findUserProfileByJwt(jwt);
        
		Review review=reviewService.createReview(req, user);
        
		return new ResponseEntity<Review>(review,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/coupon/{couponId}")
	public ResponseEntity<List<Review>> getCouponsReviewHandler(@PathVariable Long couponId){

		List<Review>reviews=reviewService.getAllReview(couponId);

		return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);
	}

}

