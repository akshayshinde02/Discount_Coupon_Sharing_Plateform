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
import com.coupons.models.Rating;
import com.coupons.models.User;
import com.coupons.request.RatingRequest;
import com.coupons.services.RatingService;
import com.coupons.services.UserService;


@RestController
@RequestMapping("/user/ratings")
public class RatingController {
	
	private UserService userService;
	private RatingService ratingServices;
	
	public RatingController(UserService userService,RatingService ratingServices) {
		this.ratingServices=ratingServices;
		this.userService=userService;
	}

	@PostMapping("/create")
	public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest req,@RequestHeader("Authorization") String jwt) throws UserException, CouponException{
		User user=userService.findUserProfileByJwt(jwt);
		Rating rating=ratingServices.createRating(req, user);
		return new ResponseEntity<>(rating,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/coupon/{couponId}")
	public ResponseEntity<List<Rating>> getCouponsReviewHandler(@PathVariable Long couponId){
	
		List<Rating> ratings=ratingServices.getCouponsRating(couponId);
		return new ResponseEntity<>(ratings,HttpStatus.OK);
	}
}

