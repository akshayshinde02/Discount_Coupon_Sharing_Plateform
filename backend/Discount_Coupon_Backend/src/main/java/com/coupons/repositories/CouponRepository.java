package com.coupons.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coupons.enums.CouponStatus;
import com.coupons.enums.PaymentStatus;
import com.coupons.models.Coupon;
import com.coupons.models.User;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // fetch all coupons for the user
    @Query("SELECT c FROM Coupon c WHERE c.user = :user")
    List<Coupon> findAllByUser(User user);

    @Query("SELECT c FROM Coupon c WHERE c.id = :id AND c.expirationDate > CURRENT_DATE AND c.couponStatus = :status")
    Optional<Coupon> findById(Long id, CouponStatus status);

    // fetch all coupons
    @Query("SELECT c FROM Coupon c")
    List<Coupon> findAllCoupons();

    // fetch all coupons expira
    // @Query("SELECT c FROM Coupon c WHERE c.expirationDate > CURRENT_DATE")
    // List<Coupon> findAllActiveCoupons();

    // fetch all coupons by category
    @Query("SELECT c FROM Coupon c WHERE c.category = :category")
    List<Coupon> findAllByCategory(String category);

    // fetch all coupons that are not expirated and status is unused
    @Query("SELECT c FROM Coupon c WHERE c.expirationDate >= CURRENT_DATE AND c.couponStatus = :status")
    List<Coupon> findAllActiveCouponsByStatus(CouponStatus status);

    // fetch all coupons who's discount value is greater than or equals to input discount
    @Query("SELECT c FROM Coupon c WHERE c.couponDiscount >= :discount")
    List<Coupon> findAllWithDiscountGreaterThanEqual(int discount);

    // fetch all coupons created date in between startDate and endDate
    @Query("SELECT c FROM Coupon c WHERE c.createdAt BETWEEN :startDate AND :endDate")
    List<Coupon> findAllCreatedBetween(LocalDateTime startDate,LocalDateTime endDate);

    // fetch all coupons from coupon code
    @Query("SELECT c FROM Coupon c WHERE c.couponCode = :couponCode")
    Optional<Coupon> findByCouponCode(String couponCode);

    // fetch all coupons user favourites
    @Query("SELECT c FROM Coupon c WHERE c.favorite.user.id = :userId")
    List<Coupon> findAllFavoritedByUser(Long userId);

    // fetch all coupons user favourites
    @Query("SELECT c FROM Coupon c WHERE c.id = :id AND c.user = :user AND c.paymentStatus = :paymentStatus")
    List<Coupon> findAllByPaymentStatus(Long id, User user, PaymentStatus paymentStatus);

}
