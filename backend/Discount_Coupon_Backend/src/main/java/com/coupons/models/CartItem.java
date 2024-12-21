// package com.coupons.models;

// import java.math.BigDecimal;

// import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonIdentityInfo;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
// public class CartItem {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private int quantity;
//     private BigDecimal unitPrice;
//     private BigDecimal totalPrice;

    
//     @ManyToOne
//     @JoinColumn(name = "coupon_id")
//     private Coupon coupon;

//     @ManyToOne(cascade = CascadeType.ALL)
//     @JsonManagedReference
//     private Cart cart;

//    public void setTotalPrice(){
//     this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
//    }

    
// }
