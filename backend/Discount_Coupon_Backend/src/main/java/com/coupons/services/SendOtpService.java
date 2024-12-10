package com.coupons.services;

import com.coupons.exceptions.OTPException;

public interface SendOtpService {
    
    public String generateAndSaveOTP(String email);

    public boolean verifyOTP(String email, String otp) throws OTPException;
}
