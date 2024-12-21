// package com.coupons.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.coupons.exceptions.CartException;
// import com.coupons.exceptions.CouponException;
// import com.coupons.exceptions.UserException;
// import com.coupons.models.Cart;
// import com.coupons.models.CartItem;
// import com.coupons.models.User;
// import com.coupons.repositories.CartRepository;
// import com.coupons.response.ApiResponse;
// import com.coupons.services.CartItemService;
// import com.coupons.services.CartService;
// import com.coupons.services.UserService;
// import com.fasterxml.jackson.core.JsonProcessingException;

// @RestController
// @RequestMapping("/api/cart_items")
// public class CartItemController {

//     @Autowired
//     private CartItemService cartItemService;

//     @Autowired
//     private CartService cartService;

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private CartRepository cartRepository;

//     @PostMapping("/item/add")
//     public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization") String jwt,
//                                                      @RequestParam Long couponId) throws CartException,JsonProcessingException,UserException,CouponException{
//         try {

//             User user = userService.findUserProfileByJwt(jwt);
//             Cart cart =  cartRepository.findByUserId(user.getId());
//             Long cartId = cart.getId();

//             cartItemService.addItemToCart(cartId, couponId);

            

//             ApiResponse response = new ApiResponse();
//             response.setMessage("Add Item Success");
//             response.setStatus(true);

//             return ResponseEntity.ok(response);
//         } catch (CartException e) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         }
//     }

//     @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
//     public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) throws CouponException{
//         try {
//             cartItemService.removeItemFromCart(cartId, itemId);
//             ApiResponse response = new ApiResponse();
//             response.setMessage("Remove Item Success");
//             response.setStatus(true);

//             return ResponseEntity.ok(response);
//         } catch (CartException e) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);

//         }
//     }

   
//     // @DeleteMapping("/{cartItemId}")

//     // public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long
//     // cartItemId,
//     // @RequestHeader("Authorization") String jwt) throws CartException,
//     // UserException {

//     // User user = userService.findUserProfileByJwt(jwt);

//     // cartItemService.removeCartItem(user.getId(), cartItemId);

//     // ApiResponse res=new ApiResponse("Item Remove From Cart",true);

//     // return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);

//     // }

//     // @PutMapping("/{cartItemId}")
//     // public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long
//     // cartItemId, @RequestBody CartItem cartItem,
//     // @RequestHeader("Authorization")String jwt) throws CartException,
//     // UserException{

//     // User user=userService.findUserProfileByJwt(jwt);

//     // CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(),
//     // cartItemId, cartItem);

//     // return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);

//     // }
// }
