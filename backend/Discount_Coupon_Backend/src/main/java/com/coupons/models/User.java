package com.coupons.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.coupons.enums.AccountStatus;
import com.coupons.enums.OathProvider;
import com.coupons.enums.SignupMethod;
import com.coupons.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserToken> tokens;

    @Enumerated(value = EnumType.STRING)
    private OathProvider oathProvider = OathProvider.SELF;
    // private String oauthId;

    // @Enumerated(value = EnumType.STRING)
    // private SignupMethod signupMethod;
    @Lob
    private byte[] profilePicture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isverified = false;
    private String badge = "Normal";
    private String level = "Basic";
    private Long points = 0L;
    private Long coupenUploadedCount = 0L;
    private Long coupenRedemedCount = 0L;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus accountStatus;
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coupon> coupons = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> userFav = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Embedded
    @ElementCollection
    @CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name = "user_id"))
    private List<PaymentInformation> paymentInformation = new ArrayList<>();

    private LocalDateTime createdAt;

}
