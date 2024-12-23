package com.coupons.services;

import org.springframework.stereotype.Service;

import com.coupons.exceptions.CouponException;
import com.coupons.models.Cart;
import com.coupons.models.CartItem;
import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.repositories.CartRepository;
import com.coupons.request.CartRequest;

@Service
public class CartServiceImpl implements CartService{
	
	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private CouponService couponService;
	
	
	public CartServiceImpl(CartRepository cartRepository,CartItemService cartItemService,
    CouponService couponService) {
		this.cartRepository=cartRepository;
		this.couponService=couponService;
		this.cartItemService=cartItemService;
	}

	@Override
	public Cart createCart(User user) {
		
		Cart cart = new Cart();
		cart.setUser(user);
		Cart createdCart=cartRepository.save(cart);
		return createdCart;
	}
	
	public Cart findUserCart(Long userId) {
		Cart cart =	cartRepository.findByUserId(userId);
		int totalPrice=0;
		int totalItem=0;
		for(CartItem cartsItem : cart.getCartItems()) {
			totalPrice+=cartsItem.getPrice();
			totalItem+=cartsItem.getQuantity();
		}
		
		cart.setTotalPrice(totalPrice);
		cart.setTotalItem(cart.getCartItems().size());
		cart.setTotalItem(totalItem);
		
		return cartRepository.save(cart);
		
	}

	@Override
	public String addCartItem(Long userId, CartRequest req) throws CouponException {
		Cart cart=cartRepository.findByUserId(userId);

		Coupon coupon=couponService.getSingleCoupon(req.getCouponId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, coupon,userId);
		
		if(isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setCoupon(coupon);
			cartItem.setCart(cart);
			cartItem.setQuantity(1);
			cartItem.setUserId(userId);
			
			
			int price=req.getPrice();
			cartItem.setPrice(price);
			
			CartItem createdCartItem=cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
		}
		
		
		return "Item Add To Cart";
	}


// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.HashSet;
// import java.util.Optional;
// import java.util.Set;
// import java.util.stream.Collectors;

// import org.hibernate.Hibernate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// // import com.coupons.dtos.CartDto;
// // import com.coupons.dtos.CartItemDto;
// import com.coupons.exceptions.CartException;
// import com.coupons.exceptions.CouponException;
// import com.coupons.exceptions.UserException;
// import com.coupons.models.Cart;
// import com.coupons.models.Coupon;
// import com.coupons.models.User;
// import com.coupons.repositories.CartRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class CartServiceImpl implements CartService {

//     @Autowired
//     private CartRepository cartRepository;

//     @Autowired
//     private CouponService couponService;

//     @Override
//     public Cart createCartForUser(User user) throws UserException {
        
//         Cart cart = new Cart();
//         cart.setUser(user);

//         return cartRepository.save(cart);
//     }


//     @Override
//     public Cart addCouponToCart(Long cartId, Long couponId) throws CartException,CouponException {
        
//         Cart cart = getCart(cartId);

//         if(cart==null){
//             throw new CartException("No cart found for given id!!");
//         }

//         Coupon coupon = couponService.getSingleCoupon(couponId);
//         if(coupon==null){
//             throw new CouponException("No coupon found for given id!!");
//         }

//         if(cart.getCoupons().contains(coupon)){
//             throw new CouponException("Coupon is already in the cart!!");
//         }

//         cart.addCoupon(coupon); 
//         // coupon.setCart(cart);

//         return cartRepository.save(cart);
//     }

//     @Override
//     public Cart removeCouponFromCart(Long cartId, Long couponId) throws CartException {
       
//         Cart cart = getCart(cartId);

//         if(cart==null){
//             throw new CartException("No cart found for given id!!");
//         }
//         Coupon couponToRemove = cart.getCoupons().stream()
//         .filter(c-> c.getId().equals(couponId))
//         .findFirst()
//         .orElseThrow(() -> new CartException("Coupon not Found in the cart!"));

//         cart.removeCoupon(couponToRemove);
//         return cartRepository.save(cart);
//     }

//     @Override
//     public Set<Coupon> getCouponsInCart(Long cartId) throws CartException {
      
//         Set<Coupon> coupons = getCart(cartId).getCoupons();

//         if(coupons==null){
//             throw new CartException("No coupons found for given cartid!!");
//         }

//         return coupons;
//     }

//     @Override
//     public Cart getCart(Long cartId) throws CartException {
//        return cartRepository.findById(cartId)
//        .orElseThrow(()-> new CartException("Cart not found!"));
//     }


//     @Override
//     public Cart findUserCart(Long userId) {
        
//        return cartRepository.findByUserId(userId);
//     }

//     public Cart saveCart(Cart cart) {
//         return cartRepository.save(cart);
//     }



// (=----------------------------------------------------------------------------------------------------------)
    

    // @Override
    // public CartDto getCart(Long id) throws CartException {

    //     Cart cart = cartRepository.findById(id)
    //             .orElseThrow(() -> new CartException("Cart not found!"));

    //     // BigDecimal totalAmount = cart.getTotalPrice();

    //     // System.out.println("-----------------"+totalAmount);
    //     // cart.setTotalPrice(totalAmount);
    //     // return cartRepository.save(cart);
    //     CartDto cartDTO = new CartDto();
    //     cartDTO.setId(cart.getId());
    //     cartDTO.setTotalPrice(cart.getTotalPrice());
    //     cartDTO.setItems(cart.getItems().stream().map(item -> {
    //         CartItemDto itemDTO = new CartItemDto();
    //         itemDTO.setId(item.getId());
    //         itemDTO.setQuantity(item.getQuantity());
    //         itemDTO.setUnitPrice(item.getUnitPrice());
    //         itemDTO.setTotalPrice(item.getTotalPrice());
    //         itemDTO.setCouponName(item.getCoupon().getCouponName());
    //         return itemDTO;
    //     }).collect(Collectors.toList()));
    //     return cartDTO;

    // }

    // @Transactional
    // @Override
    // public void clearCart(Long id) {

    //     Cart cart = new Cart();
    //     cartItemRepository.deleteAllByCartId(id);
    //     cart.getItems().clear();
    //     cartRepository.deleteById(id);
    // }

    // @Override
    // public BigDecimal getTotalPrice(Long id) throws CartException {
    //     CartDto cart = getCart(id);
    //     return cart.getTotalPrice();
    // }

    // @Override
    // public Long initializeNewCart(User user) {

    //     Cart newCart = new Cart();
    //     newCart.setUser(user);
    //     newCart.setCreatedAt(LocalDateTime.now());
    //     return cartRepository.save(newCart).getId();
    // }

    // @Override
    // public Cart getCartByUserId(Long userId) {
    //     return cartRepository.findByUserId(userId);
    // }

    // @Autowired
    // private CouponService couponService;

    // @Autowired
    // private CartItemService cartItemService;

    // @Override
    // public String addCartItems(Long userId, Long couponId) throws
    // CouponException, CartException, UserException {

    // Cart cart = cartRepository.findByUserId(userId);
    // Coupon coupon = couponService.getSingleCoupon(couponId);

    // CartItem isPresent = cartItemService.isCartItemExist(cart, coupon, userId);

    // if (isPresent == null) {

    // CartItem cartItem = new CartItem();
    // cartItem.setCoupon(coupon);
    // cartItem.setCart(cart);
    // cartItem.setQuantity(1);
    // cartItem.setUserId(userId);
    // cartItem.setPrice(coupon.getPrice());

    // CartItem createdCartItem = cartItemService.createCartItem(cartItem);

    // System.out.println("Created CartItem ID: " + createdCartItem.getId());
    // System.out.println("Cart Items Before Adding: " +
    // cart.getCartItems().size());

    // cart.getCartItems().add(createdCartItem);

    // Coupon c = cartItem.getCoupon();
    // System.out.println("Cart Items After Adding: " + cart.getCartItems().size());
    // System.out.println("Cart Items After Adding: " + c.getCouponName());

    // return "Item added to cart successfully!";
    // }
    // else{

    // return "Item already exists!";
    // }

    // }

    // @Override
    // public Cart createCart(User user) {

    // Cart cart = new Cart();
    // cart.setUser(user);
    // cart.setCreatedAt(LocalDateTime.now());
    // Cart createdCart = cartRepository.save(cart);
    // return createdCart;
    // }

    // @Override
    // public Cart findUserCart(Long userId) throws CartException {

    // // Cart cart = cartRepository.findByUserId(userId);
    // // if (cart == null) {
    // // throw new CartException("No cart found for the given user.");
    // // }
    // // int totalPrice = 0;
    // // int totalItems = 0;
    // // try {

    // // Set<CartItem> set = cart.getCartItems();

    // // System.out.println("-----------------------------"+set.size());
    // // System.out.println("--------------------"+cart.getCartItems());
    // // for (CartItem cartItem : cart.getCartItems()) {
    // // totalPrice += cartItem.getPrice();
    // // totalItems += cartItem.getQuantity();
    // // }

    // // cart.setTotalPrice(totalPrice);
    // // cart.setTotalItems(totalItems);
    // // } catch (Exception e) {
    // // throw new CartException("Exception occured.");
    // // }

    // // return cartRepository.save(cart);

    // try {
    // Cart cart = cartRepository.findByUserId(userId);
    // if (cart == null) {
    // throw new CartException("No cart found for the given user.");
    // }

    // System.out.println("Cart Items After Adding: " + cart.getCartItems().size());

    // cart.setCartItems(new HashSet<>(cart.getCartItems())); // Ensure you're
    // working with a copy
    // return cart;
    // } catch (Exception e) {
    // throw new CartException("Cannot set the values: " + e.getMessage());
    // }
    // }

}
