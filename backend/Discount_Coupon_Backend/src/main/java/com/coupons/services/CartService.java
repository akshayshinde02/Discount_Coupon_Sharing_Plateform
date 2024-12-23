package com.coupons.services;

// import java.math.BigDecimal;
// import java.util.Set;

// import com.coupons.dtos.CartDto;
// import com.coupons.exceptions.CartException;
import com.coupons.exceptions.CouponException;
// import com.coupons.exceptions.UserException;
import com.coupons.models.Cart;
// import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.request.CartRequest;

public interface CartService {

    public Cart createCart(User user);
	
	public String addCartItem(Long userId,CartRequest req) throws CouponException;
	
	public Cart findUserCart(Long userId);


    // public Cart createCartForUser(User user) throws UserException;

    // public Cart addCouponToCart(Long cartId, Long couponId) throws CartException,CouponException;

    // public Cart removeCouponFromCart(Long cartId, Long couponId) throws CartException;
    
    // public Set<Coupon> getCouponsInCart(Long cartId) throws CartException;
    
    // public Cart getCart(Long cartId) throws CartException;

    // public Cart findUserCart(Long userId);

    // public Cart saveCart(Cart cart);



































    // public CartDto getCart(Long id) throws CartException;

    // public void clearCart(Long id);

    // public BigDecimal getTotalPrice(Long id) throws CartException;

    // public Long initializeNewCart(User user);

    // public Cart getCartByUserId(Long userId);

    // public Cart createCart(User user) throws CouponException, CartException;

    // public Cart findUserCart(Long userId) throws CartException;
    
    // public String addCartItems(Long userId,  Long couponId) throws CouponException,CartException, UserException;

    // public List<Cart> getAllCartItems(Long userId) throws CartException;

    // public Cart findCartById(Long cartId) throws CartException;

    // public void deleteCartItems(Long cartId) throws CartException;
    
}
