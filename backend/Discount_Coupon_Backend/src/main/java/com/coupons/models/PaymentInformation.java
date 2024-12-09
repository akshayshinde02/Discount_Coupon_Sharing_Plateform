package com.coupons.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInformation {

    private String cardHolderName;
    private String cardNumber;
    private LocalDate expDate;
    private String cvv;

    
}
