package com.coupons.services;

import com.coupons.exceptions.CartException;
// import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Cart;
import com.coupons.models.CartItem;
import com.coupons.models.Coupon;
// import com.fasterxml.jackson.core.JsonProcessingException;

// import com.coupons.exceptions.UserException;
// import com.coupons.models.Cart;
// import com.coupons.models.CartItem;

public interface CartItemService {

    
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartException, UserException;
	
	public CartItem isCartItemExist(Cart cart,Coupon coupon, Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartException;
	

//     public void addItemToCart(Long cartId, Long couponId) throws CartException,JsonProcessingException,CouponException;

//     public void removeItemFromCart(Long cartId, Long couponId) throws CartException,CouponException;

//     public CartItem getCartItem(Long cartId, Long couponId) throws CartException,CouponException;
    
//     // public CartItem createCartItem(CartItem cartItem)throws CartException, UserException;

//     // public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartException, UserException;

//     // public void removeCartItem(Long userId,Long cartItemId) throws CartException, UserException;

//     // public CartItem isCartItemExist(Cart cart, Coupon coupon, Long userId)  throws CartException, UserException;

//     // public CartItem findCartItemById(Long cartItemId) throws CartException;

}
