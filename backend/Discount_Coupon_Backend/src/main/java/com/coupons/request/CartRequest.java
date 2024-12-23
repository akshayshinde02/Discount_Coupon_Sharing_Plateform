package com.coupons.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
public class CartRequest {

    private Long couponId;
    // private int quantity;
    private Integer price;

    public CartRequest() {
		
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	
	// public int getQuantity() {
	// 	return quantity;
	// }
	// public void setQuantity(int quantity) {
	// 	this.quantity = quantity;
	// }
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
    
}
