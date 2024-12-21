package com.coupons.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.dtos.OrderDto;
import com.coupons.exceptions.OrderException;
import com.coupons.exceptions.UserException;
import com.coupons.models.Order;
import com.coupons.models.User;
import com.coupons.services.OrderService;
import com.coupons.services.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
	public ResponseEntity<OrderDto> createOrderHandler(
			@RequestHeader("Authorization")String jwt) throws UserException,OrderException{
		
		User user=userService.findUserProfileByJwt(jwt);
		OrderDto order =orderService.createOrder(user);
		
		return new ResponseEntity<OrderDto>(order,HttpStatus.OK);
		
	}

    

    @GetMapping("/user")
	public ResponseEntity< List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization") 
	String jwt) throws OrderException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		List<Order> orders=orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") 
	String jwt) throws OrderException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		Order orders=orderService.findOrderById(orderId);
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
}
