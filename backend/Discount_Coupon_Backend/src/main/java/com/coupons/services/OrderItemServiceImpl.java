package com.coupons.services;

import org.springframework.stereotype.Service;

import com.coupons.models.OrderItem;
import com.coupons.repositories.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemRepository orderItemRepository;
	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository=orderItemRepository;
	}

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    
}
