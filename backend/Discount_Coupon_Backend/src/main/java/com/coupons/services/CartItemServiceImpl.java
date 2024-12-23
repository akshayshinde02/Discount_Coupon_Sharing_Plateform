package com.coupons.services;

import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupons.exceptions.CartException;
// import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Cart;
import com.coupons.models.CartItem;
import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.repositories.CartItemRepository;
// import com.coupons.repositories.CartRepository;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CartItemServiceImpl implements CartItemService{

    private CartItemRepository cartItemRepository;
	private UserService userService;
	// private CartRepository cartRepository;
	
	public CartItemServiceImpl(CartItemRepository cartItemRepository,UserService userService) {
		this.cartItemRepository=cartItemRepository;
		this.userService=userService;
	}

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getCoupon().getPrice());
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartException, UserException {
        CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		
		if(user.getId().equals(userId)) {
			
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getCoupon().getPrice());
			
			return cartItemRepository.save(item);
				
			
		}
		else {
			throw new CartException("You can't update  another users cart_item");
		}
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Coupon coupon, Long userId) {
        CartItem cartItem=cartItemRepository.isCartItemExist(cart, coupon, userId);
		
		return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartException, UserException {
        // System.out.println("userId- "+userId+" cartItemId "+cartItemId);
		
		CartItem cartItem=findCartItemById(cartItemId);
		
		User user=userService.findUserById(cartItem.getUserId());
		User reqUser=userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItem.getId());
		}
		else {
			throw new UserException("you can't remove anothor users item");
		}
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartException {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartException("cartItem not found with id : "+cartItemId);
	}
    
// public class CartItemServiceImpl {

//     @Autowired
//     private CartItemRepository cartItemRepository;

//     @Autowired
//     private CartRepository cartRepository;

//     @Autowired
//     private CartService cartService;

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private CouponService couponService;

//     // @Override
//     // public void addItemToCart(Long cartId, Long couponId) throws  CartException,CouponException {
        
//     //     Cart cart = cartRepository.findById(cartId)
//     //             .orElseThrow(() -> new CartException("Cart not found!"));
//     //     Coupon coupon = couponService.getSingleCoupon(couponId);

//     //     // find cart
//     //     CartItem cartItem = cart.getItems()
//     //     .stream()
//     //     .filter(item -> item.getCoupon().getId().equals(couponId))
//     //     .findFirst().orElse(new CartItem());


//     //     if(cartItem.getId() == null){
//     //         cartItem.setCart(cart);
//     //         cartItem.setCoupon(coupon);
//     //         cartItem.setQuantity(1);
//     //         cartItem.setUnitPrice(coupon.getPrice());
//     //     }


//     //     cartItem.setTotalPrice();
//     //     // cart.addItem(cartItem);
//     //     cartItemRepository.save(cartItem);
//     //     // cartRepository.save(cart);

//     // }

//     // @Override
//     // public void removeItemFromCart(Long cartId, Long couponId) throws CartException,CouponException {
//     //     Cart cart = cartRepository.findById(cartId)
//     //             .orElseThrow(() -> new CartException("Cart not found!"));
//     //     CartItem itemToRemove = getCartItem(cartId, couponId);
//     //     cart.removeItem(itemToRemove);
//     //     cartRepository.save(cart);
//     // }

//     // @Override
//     // public CartItem getCartItem(Long cartId, Long couponId) throws CartException,CouponException {
//     //     Cart cart = cartRepository.findById(cartId)
//     //             .orElseThrow(() -> new CartException("Cart not found!"));
//     //     return  cart.getItems()
//     //             .stream()
//     //             .filter(item -> item.getCoupon().getId().equals(couponId))
//     //             .findFirst().orElseThrow(() -> new CartException("Item not found"));
//     // }

    


//     // @Override
//     // public CartItem createCartItem(CartItem cartItem) throws CartException, UserException {
        
//     //     cartItem.setQuantity(1);
//     //     cartItem.setPrice(cartItem.getCoupon().getPrice()*cartItem.getQuantity());

//     //     CartItem createdCartItem = cartItemRepository.save(cartItem);

//     //     return createdCartItem;
//     // }

//     // @Override
//     // public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartException, UserException {
       
//     //     CartItem item = findCartItemById(id);
//     //     User user = userService.findUserById(item.getUserId());

//     //     if(user==null){
//     //         throw new UserException("No user found");
//     //     }

//     //     if(user.getId().equals(userId)){

//     //         item.setQuantity(cartItem.getQuantity());
//     //         item.setPrice(item.getPrice());

//     //         return cartItemRepository.save(item);

//     //     }else{
//     //         throw new CartException("You can't update  another users cart_item");
//     //     }
//     // }

//     // @Override
//     // public void removeCartItem(Long userId, Long cartItemId) throws CartException, UserException {
       
//     //     CartItem cartItem = findCartItemById(cartItemId);

//     //     User user = userService.findUserById(cartItem.getUserId());
//     //     User reqUser = userService.findUserById(userId);

//     //     if(user.getId().equals(reqUser.getId())){
//     //         cartItemRepository.deleteById(cartItem.getId());
//     //     }
//     //     else {
// 	// 		throw new UserException("you can't remove anothor users item");
// 	// 	}
//     // }

//     // @Override
//     // public CartItem isCartItemExist(Cart cart, Coupon coupon, Long userId ) throws CartException, UserException {

//     //   CartItem cartItem = cartItemRepository.isCartItemExist(cart, coupon, userId);
//     //   return cartItem;

//     // }

//     // @Override
//     // public CartItem findCartItemById(Long cartItemId) throws CartException {
       
//     //     Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
//     //     if(opt.isPresent()) {
// 	// 		return opt.get();
// 	// 	}
// 	// 	throw new CartException("cartItem not found with id : "+cartItemId);
//     // }
    
}
