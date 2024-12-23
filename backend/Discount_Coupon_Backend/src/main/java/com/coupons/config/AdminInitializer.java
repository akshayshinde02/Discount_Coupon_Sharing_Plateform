package com.coupons.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.coupons.enums.AccountStatus;
import com.coupons.enums.UserRole;
import com.coupons.models.User;
import com.coupons.repositories.UserRepository;


@Component
public class AdminInitializer implements CommandLineRunner {

    private UserRepository userRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = userRepository.findByEmail(adminEmail);
       
        if(user==null){

            User admin = new User();
            admin.setFirstName("firstName");
            admin.setLastName("lastName");
            admin.setEmail(adminEmail);
            admin.setIsverified(true);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        }
        //  else{
        //     throw new Exception("Something went wrong while creating admin");
        // }
    }

}
