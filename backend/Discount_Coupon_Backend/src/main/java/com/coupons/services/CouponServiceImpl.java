package com.coupons.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coupons.enums.CouponStatus;
import com.coupons.enums.PaymentStatus;
import com.coupons.exceptions.CouponException;
import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.repositories.CouponRepository;
import com.coupons.repositories.UserRepository;
import com.coupons.request.CouponRequest;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Coupon createCoupon(CouponRequest couponRequest, User user) throws CouponException {

        try {
            Coupon coupon = new Coupon();
            coupon.setCouponName(couponRequest.getCouponName());
            coupon.setCouponDescription(couponRequest.getCouponDescription());
            coupon.setPrice(couponRequest.getPrice());
            coupon.setExpirationDate(couponRequest.getExpirationDate());
            coupon.setCouponCode(passwordEncoder.encode(couponRequest.getCouponCode()));
            coupon.setCategory(couponRequest.getCategoryName());
            coupon.setTermsAccepted(couponRequest.getTermsAccepted());
            coupon.setPaymentStatus(PaymentStatus.NOTINITIATED);
            coupon.setCreatedAt(LocalDateTime.now());
            coupon.setUser(user);

             if(couponRequest.getExpirationDate()!=null && couponRequest.getExpirationDate().isAfter(LocalDate.now())){
                coupon.setCouponStatus(CouponStatus.UNUSED);
            }else{
                coupon.setCouponStatus(CouponStatus.USED);
            }

            user.getCoupons().add(coupon);

            Coupon savedcoupon = couponRepository.save(coupon);

            userRepository.save(user);
            // Review review = new Review();
            // review.setCoupon(savedcoupon);

            return savedcoupon;

        } catch (Exception e) {
            throw new CouponException("An error occurred while creating the coupon.");
        }

    }

    @Override
    public Coupon getSingleCoupon(Long id) throws CouponException {

        Optional<Coupon> coupon = couponRepository.findById(id, CouponStatus.UNUSED);

        if (coupon.isEmpty()) {
            throw new CouponException("No coupon found for given Id or Coupon may expire!");
        }

        return coupon.get();
    }

    @Override
    public Coupon updateCoupon(CouponRequest couponRequest, Long couponId) throws CouponException {

        try {

            Coupon coupon = getSingleCoupon(couponId);
            coupon.setCouponName(couponRequest.getCouponName());
            coupon.setCouponDescription(couponRequest.getCouponDescription());
            coupon.setPrice(couponRequest.getPrice());
            coupon.setExpirationDate(couponRequest.getExpirationDate());
            coupon.setCouponCode(passwordEncoder.encode(couponRequest.getCouponCode()));
            coupon.setCategory(couponRequest.getCategoryName());
            coupon.setTermsAccepted(couponRequest.getTermsAccepted());
            coupon.setPaymentStatus(PaymentStatus.NOTINITIATED);
            coupon.setCreatedAt(LocalDateTime.now());
            coupon.setUser(coupon.getUser());
            
            if(couponRequest.getExpirationDate()!=null && couponRequest.getExpirationDate().isAfter(LocalDate.now())){
                coupon.setCouponStatus(CouponStatus.UNUSED);
            }else{
                coupon.setCouponStatus(CouponStatus.USED);
            }
            return couponRepository.save(coupon);

        } catch (Exception e) {
            throw new CouponException("An error occurred while updating the coupon.");
        }

    }

    @Override
    public void deleteCoupon(Long couponId) throws CouponException {

        try {

           Optional<Coupon> coupon = couponRepository.findById(couponId);

           User user = coupon.get().getUser();

           if(user!=null){
            user.getCoupons().remove(coupon.get());
            userRepository.save(user);
           }

            couponRepository.deleteById(couponId);
        } catch (Exception e) {
            throw new CouponException("An error occurred while deleting the coupon.");
        }

    }

    @Override
    public List<Coupon> getAllCouponsForUser(User user) throws CouponException {

        List<Coupon> coupons = couponRepository.findAllByUser(user);
        if(coupons.isEmpty()){
            throw new CouponException("No coupon found for user!");
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCouponsByCategory(String category) throws CouponException {

        List<Coupon> coupons =  couponRepository.findAllByCategory(category);
        if(coupons.isEmpty()){
            throw new CouponException("No coupon found for category!");
        }

        return coupons;
    }

    @Override
    public List<Coupon> getActiveCoupons(CouponStatus status) throws CouponException {

        List<Coupon> coupons =  couponRepository.findAllActiveCouponsByStatus(status);
        if(coupons.isEmpty()){
            throw new CouponException("No coupon found for user!");
        }

        return coupons;
    }

    // @Override
    // public List<Coupon> getDiscountCoupons(int discount) throws CouponException {
       
    //     List<Coupon> coupons =  couponRepository.findAllWithDiscountGreaterThanEqual(discount);
    //     if(coupons.isEmpty()){
    //         throw new CouponException("No coupon found for discount!");
    //     }

    //     return coupons;
    // }

    @Override
    public Optional<Coupon> getCouponsFromCouponCode(String couponCode) throws CouponException {

        Optional<Coupon> coupons =  couponRepository.findByCouponCode(couponCode);
        if(coupons.isEmpty()){
            throw new CouponException("No coupon found for coupon code!");
        }

        return coupons;
    }

    // @Override
    // public List<Coupon> getCouponsFavoritedByUser(Long userId) throws CouponException {
       
    //     List<Coupon> coupons =  couponRepository.findAllFavoritedByUser(userId);
    //     if(coupons.isEmpty()){
    //         throw new CouponException("No coupon found for user Id!");
    //     }

    //     return coupons;
    // }

    @Override
    public List<Coupon> getCouponsByPaymentStatus(Long couponId, User user, PaymentStatus paymentStatus)
            throws CouponException {
        
                List<Coupon> coupons = couponRepository.findAllByPaymentStatus(couponId, user, paymentStatus);

                return coupons;
    }

    @Override
	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

}
