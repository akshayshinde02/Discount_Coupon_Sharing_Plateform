package com.coupons.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coupons.models.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderStatus = CONFIRMED")
    public List<Order> getUsersOrders(Long userId);
    
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderCoupons WHERE o.id = :orderId")
    Optional<Order> findByIdWithCoupons(@Param("orderId") Long orderId);

}
