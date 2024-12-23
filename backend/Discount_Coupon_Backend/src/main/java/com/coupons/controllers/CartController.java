package com.coupons.controllers;

// import com.coupons.dtos.CartDto;
import com.coupons.exceptions.CartException;
import com.coupons.exceptions.CouponException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Cart;
import com.coupons.models.Coupon;
import com.coupons.models.User;
import com.coupons.request.CartRequest;
import com.coupons.response.ApiResponse;
import com.coupons.services.CartService;
import com.coupons.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/user/cart")
public class CartController {
	
	private CartService cartService;
	private UserService userService;
	
	public CartController(CartService cartService,UserService userService) {
		this.cartService=cartService;
		this.userService=userService;
	}
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Cart cart=cartService.findUserCart(user.getId());
		
		System.out.println("cart - "+cart.getUser().getEmail());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartRequest req, @RequestHeader("Authorization") String jwt) throws UserException, CouponException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res= new ApiResponse("Item Added To Cart Successfully",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}
	





// @RestController
// @RequestMapping("/api/cart")
// public class CartController {

//     @Autowired
//     private CartService cartService;

//     @Autowired 
//     private UserService userService;

//     @PostMapping("/{cartId}/coupons/{couponId}")
//     public ResponseEntity<Cart> addCoupon(@PathVariable Long cartId, @PathVariable Long couponId) throws CouponException, CartException{
//         try {
//             Cart updatedCart = cartService.addCouponToCart(cartId, couponId);
//             return ResponseEntity.ok(updatedCart);
//         } catch (CartException e) {
//             throw new CartException("Cart not found, Coupon not added to Cart!");
//         }
//     }

//     @DeleteMapping("/{cartId}/coupons/{couponId}")
//     public ResponseEntity<Cart> removeCoupon(@PathVariable Long cartId, @PathVariable Long couponId) throws CartException{
//         try {
//             Cart updatedCart = cartService.removeCouponFromCart(cartId, couponId);
//             return ResponseEntity.ok(updatedCart);
//         } catch (CartException e) {
//             throw new CartException("Invalid cartId or couponId, Coupon cannot being removed!");
//         }
//     }

//      // Get all coupons in the cart
//      @GetMapping("/{cartId}/coupons")
//      public ResponseEntity<Set<Coupon>> getCoupons(@PathVariable Long cartId) throws CartException{
//          try {
//              Set<Coupon> coupons = cartService.getCouponsInCart(cartId);
//              return ResponseEntity.ok(coupons);
//          } catch (CartException e) {
//             throw new CartException("Invalid cartId, Coupons in cart not found!");
//          }
//      }
 
//      // Get cart by ID
//      @GetMapping("/{cartId}")
//      public ResponseEntity<Cart> getCart(@PathVariable Long cartId) throws CartException{
//          try {
//              Cart cart = cartService.getCart(cartId);
//              return ResponseEntity.ok(cart);
//          } catch (CartException e) {
//             throw new CartException("Cart not found for user!");
//          }
//      }






















    

    // @GetMapping("{cartId}/my-cart")
    // public ResponseEntity<CartDto> getCart(@PathVariable Long cartId) throws CartException{
    //     try {
    //         CartDto cart = cartService.getCart(cartId);

    //         return new ResponseEntity<CartDto>(cart,HttpStatus.OK);
    //     } catch (CartException e) {
    //         return new ResponseEntity<CartDto>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // @DeleteMapping("/{cartId}/clear")
    // public ResponseEntity<ApiResponse> clearCart( @PathVariable Long cartId)throws CartException {
    //     try {
    //         cartService.clearCart(cartId);

    //         ApiResponse response = new ApiResponse();
    //         response.setMessage("Clear Cart Success!");
    //         response.setStatus(true);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // @GetMapping("/{cartId}/cart/total-price")
    // public ResponseEntity<BigDecimal> getTotalAmount( @PathVariable Long cartId) {
    //     try {
    //         BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            
    //         return ResponseEntity.ok(totalPrice);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }
    

   
    // @GetMapping("/")
    // public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException, CartException{

    //     User user = userService.findUserProfileByJwt(jwt);
    //     Cart cart = cartService.findUserCart(user.getId());

    //     System.out.println("------------------"+cart.getCartItems().size());


    //     return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    // }

    
    // @PutMapping("/add")
    // public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartRequest req, @RequestHeader("Authorization") String jwt) throws UserException,CouponException, CartException{
        
    //     User user = userService.findUserProfileByJwt(jwt);
    //     // System.out.println("user--------------------------"+user);


    //     String output = cartService.addCartItems(user.getId(), req.getCouponId());

    //     ApiResponse res= new ApiResponse(output,true);
		
	// 	return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    // }
}
