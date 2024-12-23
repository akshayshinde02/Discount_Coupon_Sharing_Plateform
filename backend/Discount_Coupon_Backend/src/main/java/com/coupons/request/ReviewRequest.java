package com.coupons.request;

public class ReviewRequest {

    private Long couponId;
    private String review;

    public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
    
}
