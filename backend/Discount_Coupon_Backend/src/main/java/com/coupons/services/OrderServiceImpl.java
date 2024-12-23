package com.coupons.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupons.dtos.OrderDto;
import com.coupons.enums.PaymentStatus;
import com.coupons.exceptions.OrderException;
import com.coupons.models.Cart;
import com.coupons.models.CartItem;
import com.coupons.models.Coupon;
import com.coupons.models.Order;
import com.coupons.models.OrderItem;
import com.coupons.models.OrderStatus;
import com.coupons.models.User;
import com.coupons.repositories.OrderItemRepository;
import com.coupons.repositories.OrderRepository;
import com.coupons.repositories.UserRepository;


@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderRepository orderRepository;
	private CartService cartService;
	private UserRepository userRepository;
	private OrderItemService orderItemService;
	private OrderItemRepository orderItemRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository,CartService cartService,
			UserRepository userRepository,
			OrderItemService orderItemService,OrderItemRepository orderItemRepository) {
		this.orderRepository=orderRepository;
		this.cartService=cartService;
		this.userRepository=userRepository;
		this.orderItemService=orderItemService;
		this.orderItemRepository=orderItemRepository;
	}

	@Override
	public Order createOrder(User user) {
				
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setCoupon(item.getCoupon());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setUserId(item.getUserId());
			
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus(OrderStatus.PENDING);
		createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder=orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
		
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		
		
		return orderRepository.save(order);
	}

	

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt=orderRepository.findById(orderId);

		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders=orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		
		orderRepository.deleteById(orderId);
		
	}

   

}




























// @Service
// public class OrderServiceImpl implements OrderService{

//     @Autowired
//     private OrderRepository orderRepository;

//     @Autowired
//     private CartService cartService;

//     @Autowired
//     private UserRepository userRepository;


//     @Override
//     public OrderDto  createOrder(User user) throws OrderException {
//         Cart cart = cartService.findUserCart(user.getId());
    
//         // if (cart.getCoupons().isEmpty()) {
//         //     throw new OrderException("Cart is empty. Cannot create an order with no coupons.");
//         // }
    
//         Set<Coupon> couponsItems = new HashSet<>();
//         BigDecimal sum = BigDecimal.ZERO;

//         Order createdOrder = new Order();
//         createdOrder.setUser(user);
//         createdOrder.setOrderDateTime(LocalDateTime.now());
//         createdOrder.setOrderStatus(OrderStatus.PENDING);
//         createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
//         createdOrder.setCreatedAt(LocalDateTime.now());
    
    
//         // for (Coupon cartItem : cart.getCoupons()) {
//         //     sum = sum.add(cartItem.getPrice());
//         //     // cartItem.setOrder(createdOrder);  // Associate each coupon with the order
//         //     // couponsItems.add(cartItem);
//         // }
        
        
//         createdOrder.setTotalPrice(sum);
//         // createdOrder.addOrderCoupons(cart.getCoupons());
//         // createdOrder.setOrderCoupons(addOrderCoupons(cart.getCoupons()));
//         // System.out.println("0------------------------------------------"+createdOrder.getOrderCoupons().toString());
//         Order savedOrder = orderRepository.save(createdOrder);
//         // System.out.println("01------------------------------------------"+savedOrder.getOrderCoupons().toString());
        
//         // Clear the cart after order creation
//         // cart.getCoupons().clear();

//         // cartService.saveCart(cart);
    
//         return new OrderDto(  
//             savedOrder.getId(),
//         savedOrder.getOrderDateTime(),
//         savedOrder.getTotalPrice(),
//         savedOrder.getOrderStatus(),
//         savedOrder.getCreatedAt(),
//         savedOrder.getOrderItems());
//     }
    
//     @Override
//     public Order findOrderById(Long orderId) throws OrderException {
//         Optional<Order> opt=orderRepository.findById(orderId);
		
//         // System.out.println("1------------------------------"+opt.get().getOrderItems());
//         // System.out.println("2------------------------------"+opt.get().getOrderStatus());
// 		if(opt.isPresent()) {
// 			return opt.get();
// 		}
// 		throw new OrderException("order not exist with id "+orderId);
//     }

//     @Override
//     public List<Order> usersOrderHistory(Long userId) {
//        List<Order> orders = orderRepository.getUsersOrders(userId);
//        return orders;
//     }

//     // @Override
//     // public Order placedOrder(Long orderId) throws OrderException {
//     //     Order order = findOrderById(orderId);
//     //     order.setOrderStatus(OrderStatus.CONFIRMED);
//     // }

//     @Override
//     public Order confirmedOrder(Long orderId) throws OrderException {
//         Order order=findOrderById(orderId);
// 		order.setOrderStatus(OrderStatus.CONFIRMED);
		
		
// 		return orderRepository.save(order);
//     }

//     @Override
//     public Order cancledOrder(Long orderId) throws OrderException {
//         Order order=findOrderById(orderId);
// 		order.setOrderStatus(OrderStatus.CANCELLED);
// 		return orderRepository.save(order);
//     }

//     @Override
//     public List<Order> getAllOrders() {
//         return orderRepository.findAll();
//     }

//     @Override
//     public void deleteOrder(Long orderId) throws OrderException {
//         Order order = findOrderById(orderId);
//         orderRepository.delete(order);
//     }

//     // @Override
//     // public List<Order> getUserOrders(Long userId) {
//     //   return orderRepository.getUsersOrders(userId)
//     //   .stream()
//     //   .ma
//     // }
    
// }
