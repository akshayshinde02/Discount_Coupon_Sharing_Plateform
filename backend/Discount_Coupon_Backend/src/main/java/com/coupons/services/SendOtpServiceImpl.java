package com.coupons.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coupons.models.OtpVerification;
import com.coupons.repositories.OtpRepository;

@Service
public class SendOtpServiceImpl implements SendOtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public String generateAndSaveOTP(String email) {

        // Generate Otp
        String EncodeOtp = generateOtp(6);

        String hashedOtp = new BCryptPasswordEncoder().encode(EncodeOtp);

        OtpVerification otpVerification = OtpVerification.builder()
        .otpHash(hashedOtp)
        .exTimestamp(calculateExpiryTime(5))
        .isUsed(false)
        .createdAt(LocalDateTime.now())
        .email(email)
        .build();

        otpRepository.save(otpVerification);

        return EncodeOtp;
    }

    private LocalDateTime calculateExpiryTime(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes);
    }

    private String generateOtp(int length) {
        
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for(int i=0; i<length; i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        
        OtpVerification otpRecord = otpRepository.findByEmailAndIsUsedFalse(email);

        if (otpRecord == null) {
            throw new RuntimeException("OTP not found for email: " + email);
        }
        if (otpRecord.isExpired()) {
            throw new RuntimeException("OTP has expired. Please request a new one.");
        }
        
        boolean isValid = new BCryptPasswordEncoder().matches(otp, otpRecord.getOtpHash());

        if(isValid){
            otpRecord.setIsUsed(true);
            otpRepository.save(otpRecord);
        }

        return isValid;
    }

}
