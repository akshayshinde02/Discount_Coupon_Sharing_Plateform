package com.coupons.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.coupons.models.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
