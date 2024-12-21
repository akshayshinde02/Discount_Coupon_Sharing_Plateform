package com.coupons.services;

import java.util.List;
import java.util.Set;

import com.coupons.dtos.OrderDto;
import com.coupons.exceptions.OrderException;
import com.coupons.models.Order;
import com.coupons.models.User;

public interface OrderService {

    public OrderDto createOrder(User user) throws OrderException;

    public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory(Long userId);

    // public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId)throws OrderException;

    public Order cancledOrder(Long orderId) throws OrderException;

    public List<Order>getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;

    // public Set<OrderDto> getUserOrders(Long userId);
    
    
}
