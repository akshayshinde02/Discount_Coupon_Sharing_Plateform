package com.coupons.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coupons.enums.CouponStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String couponName;
    private String couponDescription;
    private int couponDiscount;
    private Date expirationDate;

    @JsonIgnore
    private String couponCode;

    private CouponStatus couponStatus;
    private boolean termsAccepted = false;  

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviews = new ArrayList<>();

    @ManyToOne
    private Favourite favorite;

    @ManyToOne
    private User user;    

    @ManyToOne
    private Category category;

    private LocalDateTime createdAt;

    
}
