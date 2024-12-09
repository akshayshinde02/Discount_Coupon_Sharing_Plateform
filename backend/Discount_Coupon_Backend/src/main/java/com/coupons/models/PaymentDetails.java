package com.coupons.models;

import com.coupons.enums.PaymentMethod;
import com.coupons.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {

    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String paymentId;
    private String razorpayPaymentLinkId;
	private String razorpayPaymentLinkReferenceId;
	private String razorpayPaymentLinkStatus;
	private String razorpayPaymentId​;

    
}
