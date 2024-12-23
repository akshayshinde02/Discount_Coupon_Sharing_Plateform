package com.coupons.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "total_price")
    private double totalPrice;
    
    @Column(name="total_item")
    private int totalItem;

    public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Cart(Long id, User user, Set<CartItem> cartItems, double totalPrice, int totalItem) {
		super();
		this.id = id;
		this.user = user;
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
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

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

    // private BigDecimal totalPrice = BigDecimal.ZERO;

    // // @JoinColumn(name = "cart_id")
    // @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JsonProperty("couponscart")
    // private Set<Coupon> cartCoupons = new HashSet<>();


    // @OneToOne
    // @JoinColumn(name = "user_id")
    // // @JsonIgnore
    // private User user;

    // private LocalDateTime createdAt = LocalDateTime.now();

    

    // public void addCoupon(Coupon coupon) {
    //     this.cartCoupons.add(coupon);
    //     coupon.setCart(this);
    //     updateTotalAmount();
    // }

    // public void removeCoupon(Coupon coupon){
    //     this.cartCoupons.remove(coupon);
    //     coupon.setCart(null);
    //     updateTotalAmount();
    // }
    
    // public User getUser(){
    //    return this.user;
    // }

    // @JsonProperty("couponscart")
    // public Set<Coupon> getCoupons(){
    //    return this.cartCoupons;
    // }

    // public void setUser(User user){
    //    this.user = user;
    // }

    // private void updateTotalAmount() {
    //     this.totalPrice = cartCoupons.stream().
    //     map(Coupon::getPrice)
    //     .reduce(BigDecimal.ZERO, BigDecimal::add);
    // }



}
