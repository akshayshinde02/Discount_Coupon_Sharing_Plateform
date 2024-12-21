package com.coupons.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.config.JwtProvider;
import com.coupons.enums.CouponStatus;
import com.coupons.enums.PaymentStatus;
import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.repositories.CouponRepository;
import com.coupons.request.CouponRequest;
import com.coupons.response.MessageResponse;
import com.coupons.services.CouponService;
import com.coupons.services.UserService;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired 
    private UserService userService;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(
        @RequestHeader("Authorization") String jwt,
        @RequestBody CouponRequest request
    ) throws CouponException, UserException{

       User user = userService.findUserProfileByJwt(jwt);

       Coupon coupon = couponService.createCoupon(request, user);

       return new ResponseEntity<>(coupon,HttpStatus.CREATED);

    }

    // Get All Coupons of any users
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(
        @RequestHeader("Authorization") String jwt
    ) throws UserException, CouponException{

        List<Coupon> coupons = couponRepository.findAllCoupons();

        return new ResponseEntity<>(coupons,HttpStatus.OK);

    }

    // Get All Coupons of any user that coupons being active
    @GetMapping("/search/active")
    public ResponseEntity<List<Coupon>> getAllActiveCoupons(
        @RequestParam(required = false) CouponStatus active,
        @RequestHeader("Authorization") String jwt
    ) throws CouponException{

       List<Coupon> coupons = couponService.getActiveCoupons(active);

        return new ResponseEntity<>(coupons,HttpStatus.OK);
    }

    // get all coupon of single user
    @GetMapping("/user")
    public ResponseEntity<List<Coupon>> getUserCoupons(
        @RequestHeader("Authorization") String jwt
    ) throws UserException, CouponException{

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(couponService.getAllCouponsForUser(user),HttpStatus.OK);

    }

    // get valid coupon by coupon id 
    @GetMapping("/{couponId}")
    public ResponseEntity<Coupon> getCouponById(
        @PathVariable Long couponId,
        @RequestHeader("Authorization") String jwt
    ) throws CouponException{

        Coupon coupon = couponService.getSingleCoupon(couponId);

        return new ResponseEntity<>(coupon,HttpStatus.OK);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Coupon> updateCoupon(
        @PathVariable Long couponId,
        @RequestHeader("Authorization") String jwt,
        @RequestBody CouponRequest couponRequest
    ) throws CouponException{

        Coupon updatedCoupon = couponService.updateCoupon(couponRequest, couponId);

        return new ResponseEntity<>(updatedCoupon,HttpStatus.OK);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<MessageResponse> deleteCoupon(
        @PathVariable Long couponId
    ) throws CouponException{

        couponService.deleteCoupon(couponId);

        MessageResponse message = new MessageResponse("Coupon Deleted SuccessFully");

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    // get coupon by category
    @GetMapping("/search/category")
    public ResponseEntity<List<Coupon>> searchCouponByCategory(
        @RequestParam(required = false) String category,
        @RequestHeader("Authorization") String jwt
    ) throws CouponException{

       List<Coupon> coupons = couponService.getCouponsByCategory(category);

        return new ResponseEntity<>(coupons,HttpStatus.OK);
    }


    // get coupons discount is greater or equal
    // @GetMapping("/search/{discount}")
    // public ResponseEntity<List<Coupon>> getDiscountCoupons(
    //     @PathVariable int discount
    // ) throws CouponException{

    //    List<Coupon> coupons = couponService.getDiscountCoupons(discount);

    //     return new ResponseEntity<>(coupons,HttpStatus.OK);
    // }


    // get coupon by coupon code
    @GetMapping("/search/couponcode")
    public ResponseEntity<List<Coupon>> getCouponsFromCouponCode(
        @RequestParam(required = false) String category,
        @RequestHeader("Authorization") String jwt
    ) throws CouponException{

       List<Coupon> coupons = couponService.getCouponsByCategory(category);

        return new ResponseEntity<>(coupons,HttpStatus.OK);
    }


    // get user favorite coupons
    // @GetMapping("/search/favorite")
    // public ResponseEntity<List<Coupon>> getCouponsFavoritedByUser(
    //     @RequestHeader("Authorization") String jwt
    // ) throws UserException, CouponException{

    //     User user = userService.findUserProfileByJwt(jwt);
    //    List<Coupon> coupons = couponService.getCouponsFavoritedByUser(user.getId());

    //     return new ResponseEntity<>(coupons,HttpStatus.OK);
    // }

    // get user favorite coupons
    @GetMapping("/search/{couponId}/payment")
    public ResponseEntity<List<Coupon>> getCouponsByPayment(
        @PathVariable Long couponId,
        @RequestHeader("Authorization") String jwt
    ) throws UserException, CouponException{

       User user = userService.findUserProfileByJwt(jwt);
       List<Coupon> coupons = couponService.getCouponsByPaymentStatus(couponId, user, PaymentStatus.COMPLETED);

        return new ResponseEntity<>(coupons,HttpStatus.OK);
    }

    
}
