package com.coupons.services;
import java.util.List;
import java.util.Optional;


import com.coupons.enums.CouponStatus;
import com.coupons.enums.PaymentStatus;
import com.coupons.exceptions.CouponException;
import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.request.CouponRequest;

public interface CouponService {

    Coupon createCoupon(CouponRequest couponRequest, User user) throws CouponException;

    Coupon getSingleCoupon(Long id) throws CouponException;

    Coupon updateCoupon(CouponRequest couponRequest, Long couponId) throws CouponException;

    void deleteCoupon(Long couponId) throws CouponException;

    List<Coupon> getAllCouponsForUser(User user) throws CouponException;

    List<Coupon> getCouponsByCategory(String category) throws CouponException;

    List<Coupon> getActiveCoupons(CouponStatus status) throws CouponException;

    List<Coupon> getDiscountCoupons(int discount) throws CouponException;

    Optional<Coupon> getCouponsFromCouponCode(String couponCode) throws CouponException;

    List<Coupon> getCouponsFavoritedByUser(Long userId) throws CouponException;

    List<Coupon> getCouponsByPaymentStatus(Long couponId, User user, PaymentStatus paymentStatus) throws CouponException;
    
}
