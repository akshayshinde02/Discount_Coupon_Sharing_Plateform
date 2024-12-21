package com.coupons.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coupons.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
  
    public Cart findByUserId(Long userId);

}
