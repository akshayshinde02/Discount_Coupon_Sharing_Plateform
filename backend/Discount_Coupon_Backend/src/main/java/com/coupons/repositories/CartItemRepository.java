package com.coupons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coupons.models.Cart;
import com.coupons.models.CartItem;
import com.coupons.models.Coupon;
import com.coupons.models.User;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {


//     void deleteAllByCartId(Long id);

@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.coupon=:coupon  And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart")Cart cart,@Param("coupon")Coupon coupon, @Param("userId")Long userId);
    
}
