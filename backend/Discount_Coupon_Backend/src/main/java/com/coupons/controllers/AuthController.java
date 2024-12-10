package com.coupons.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.config.JwtProvider;
import com.coupons.enums.AccountStatus;
import com.coupons.enums.UserRole;
import com.coupons.exceptions.EmailServiceException;
import com.coupons.exceptions.OTPException;
import com.coupons.exceptions.UserException;
import com.coupons.models.OtpVerification;
import com.coupons.models.User;
import com.coupons.models.UserToken;
import com.coupons.repositories.OtpRepository;
import com.coupons.repositories.TokenRepository;
import com.coupons.repositories.UserRepository;
import com.coupons.request.LoginRequest;
import com.coupons.request.OtpRequest;
import com.coupons.request.OtpVerificationRequest;
import com.coupons.response.AuthResponse;
import com.coupons.services.CustomUserDetails;
import com.coupons.services.EmailServiceImpl;
// import com.coupons.services.EmailService;
import com.coupons.services.SendOtpService;

@RestController
@RequestMapping("/api/public/")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetails customUserDetails;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private SendOtpService sendOtpService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private OtpRepository otpRepository;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sentOtp(@RequestBody OtpRequest request) throws UserException,EmailServiceException{

        String email = request.getEmail();
        email = email.trim();
        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email Is Already Used With Another Account");
        }

        // Otp Verification
       String otp = sendOtpService.generateAndSaveOTP(email);

       emailService.sendEmailWithOTP(email, otp);
       
       return ResponseEntity.ok("Otp sent on email !!");
        
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) throws OTPException{

        boolean isValid = sendOtpService.verifyOTP(request.getEmail(),request.getOtp());


        if(!isValid){
            throw new OTPException("Invalid OTP");
        }
       
       return ResponseEntity.ok("Otp verified successfully !!");
        
    }


    @PostMapping("/siginup")
    public ResponseEntity<AuthResponse> createUserHandler(@Validated @RequestBody User user) throws UserException{

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);

        // email found 
        if(isEmailExist!=null){
            throw new UserException("Email Is Already Used With Another Account");
        }

        System.out.println("1------------------------------");
        
        
        Optional<OtpVerification> verifiedUser = otpRepository.findByEmailIgnoreCase(email);
        
        System.out.println("2------------------------------");

        if (verifiedUser.isEmpty() || !verifiedUser.get().getIsUsed()) {
            throw new UserException("Email verification is required before signing up");
        }
        

       User creatUser = User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(passwordEncoder.encode(password))
            .createdAt(LocalDateTime.now()) // Set createdAt field
            .role(UserRole.USER)           // Assign default role if applicable
            .accountStatus(AccountStatus.ACTIVE) // Assign default account status if applicable
            .isDeleted(false)
            .isverified(true)
            .build();

            
            userRepository.save(creatUser);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            
            var tok = UserToken.builder()
            .user(creatUser)
            .token(token)
            .expired(false)
            .build();

            tokenRepository.save(tok);


        AuthResponse auth = new AuthResponse(token, true);
        System.out.println("success"+auth.getJwt());

        return new ResponseEntity<AuthResponse>(auth,HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signinHandler(@RequestBody LoginRequest loginRequest) throws UserException{

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // System.out.println(username+" "+password);

        Authentication authentication = authenticate(username,password);
        System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UserException("Invalid email or password");
        }
    
        if (!user.isIsverified()) {
            throw new UserException("Email verification is required to sign in");
        }

        String token = jwtProvider.generateToken(authentication);

        var tok = UserToken.builder()
            .user(user)
            .token(token)
            .expired(false)
            .build();

            tokenRepository.save(tok);

        AuthResponse auth = new AuthResponse();
        auth.setJwt(token);
        auth.setStatus(true);
        return new ResponseEntity<>(auth,HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password){

        

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }
        
        if(!passwordEncoder.matches(password, userDetails.getPassword())){

            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }

}
