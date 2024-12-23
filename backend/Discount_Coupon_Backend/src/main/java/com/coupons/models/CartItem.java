package com.coupons.models;

// import java.math.BigDecimal;
import java.util.Objects;

// import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.fasterxml.jackson.annotation.ObjectIdGenerators;

// import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

@Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
	@ManyToOne
	private Cart cart;
	
	@ManyToOne
	private Coupon coupon;
	
	private int quantity;
	
	private Integer price;

	
	private Long userId;
	
	public CartItem() {
		
	}

	public CartItem(Long id, Cart cart,  String size, int quantity, Integer price, Long userId) {
		super();
		this.id = id;
		this.cart = cart;
		this.quantity = quantity;
		this.price = price;
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(id, price, coupon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(id, other.id) && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(coupon, other.coupon);
	}

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

    
}
