package com.coupons.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_id")
    private String orderId;
  
    @ManyToOne
	@JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    @Embedded
    private PaymentDetails paymentDetails=new PaymentDetails();

    private double totalPrice;

    private OrderStatus orderStatus;
    
    private int totalItem;
    
    private LocalDateTime createdAt;

    public Order() {
		
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

    
    
}


// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;

// import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Embedded;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// // @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Table(name = "orders")
// public class Order {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private LocalDateTime orderDateTime;

//     private BigDecimal totalPrice;

//     private OrderStatus orderStatus;

//     @Embedded
//     private PaymentDetails paymentDetails = new PaymentDetails();

//     @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//     private List<Coupon> orderCoupons = new ArrayList<>();

//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     @JsonIgnore
//     private User user;

//     private LocalDateTime createdAt;

//     public LocalDateTime getCreatedAt() {
// 		return createdAt;
// 	}

// 	public void setCreatedAt(LocalDateTime createdAt) {
// 		this.createdAt = createdAt;
// 	}

//     public Long getId() {
// 		return id;
// 	}

// 	public void setId(Long id) {
// 		this.id = id;
// 	}

// 	public User getUser() {
// 		return user;
// 	}

// 	public void setUser(User user) {
// 		this.user = user;
// 	}

// 	public List<Coupon> getOrderItems() {
// 		return orderCoupons;
// 	}

// 	public void setOrderItems(List<Coupon> orderCoupons) {
// 		this.orderCoupons = orderCoupons;
// 	}

//     public LocalDateTime getOrderDateTime() {
// 		return orderDateTime;
// 	}

// 	public void setOrderDateTime(LocalDateTime orderDateTime) {
// 		this.orderDateTime = orderDateTime;
// 	}

//     public PaymentDetails getPaymentDetails() {
// 		return paymentDetails;
// 	}

// 	public void setPaymentDetails(PaymentDetails paymentDetails) {
// 		this.paymentDetails = paymentDetails;
// 	}

//     public BigDecimal getTotalPrice() {
// 		return totalPrice;
// 	}

// 	public void setTotalPrice(BigDecimal totalPrice) {
// 		this.totalPrice = totalPrice;
// 	}

//     public OrderStatus getOrderStatus() {
// 		return orderStatus;
// 	}

// 	public void setOrderStatus(OrderStatus orderStatus) {
// 		this.orderStatus = orderStatus;
// 	}

//     public void addOrderCoupons(Set<Coupon> coupons) {

//         for (Coupon coupon : coupons) {
//             if (coupon.getOrder() == null) {
//                 coupon.setOrder(this);
//                 this.orderCoupons.add(coupon);
//                 // return this.orderCoupons;
//             }
//         }
//     }

// }
