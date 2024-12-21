package com.coupons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.coupons.exceptions.EmailServiceException;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl{
    
    @Autowired
    private JavaMailSender javaMailSender;

    // @Override
    public void sendEmailWithOTP(String email, String otp) throws EmailServiceException{
       
        
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
    
            String subject = "Verification Code";
            String text = "Your One Time Password is: "+ otp;

    
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text,true);
            mimeMessageHelper.setTo(email);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailServiceException(e.getMessage());
        }
    }
    
}
