package com.coupons.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.exceptions.CouponException;
import com.coupons.models.Coupon;
import com.coupons.response.MessageResponse;
import com.coupons.services.CouponService;

@RestController
@RequestMapping("/admin/coupons")
public class AdminCouponController {

    private CouponService couponService;

    public AdminCouponController(CouponService couponService) {
		this.couponService = couponService;
	}

    // @PostMapping("/")
    //  public ResponseEntity<Coupon> createCouponHandler(
    //     @RequestHeader("Authorization") String jwt,
    //     @RequestBody CouponRequest request
    // ) throws CouponException, UserException{

    //    User user = userService.findUserProfileByJwt(jwt);

    //    Coupon coupon = couponService.createCoupon(request, user);

    //    return new ResponseEntity<>(coupon,HttpStatus.CREATED);

    // }

    @DeleteMapping("/{couponId}/delete")
    public ResponseEntity<MessageResponse> deleteCouponHandler(
        @PathVariable Long couponId
    ) throws CouponException{

        couponService.deleteCoupon(couponId);

        MessageResponse message = new MessageResponse("Coupon Deleted SuccessFully");

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping("/all")
	public ResponseEntity<List<Coupon>> findAllCoupons(){
		
		List<Coupon> coupons = couponService.getAllCoupons();
		
		return new ResponseEntity<List<Coupon>>(coupons,HttpStatus.OK);
	}
}
