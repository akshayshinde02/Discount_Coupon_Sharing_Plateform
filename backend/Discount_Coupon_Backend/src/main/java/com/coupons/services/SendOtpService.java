package com.coupons.services;


public interface SendOtpService {
    
    public String generateAndSaveOTP(String email);

    public boolean verifyOTP(String email, String otp);
}
