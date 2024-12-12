package com.coupons.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupons.config.JwtProvider;
import com.coupons.enums.AccountStatus;
import com.coupons.enums.TokenType;
import com.coupons.enums.UserRole;
import com.coupons.exceptions.EmailServiceException;
import com.coupons.exceptions.OTPException;
import com.coupons.exceptions.TokenException;
import com.coupons.exceptions.UserException;
import com.coupons.models.OtpVerification;
import com.coupons.models.User;
import com.coupons.models.UserToken;
import com.coupons.repositories.OtpRepository;
import com.coupons.repositories.TokenRepository;
import com.coupons.repositories.UserRepository;
import com.coupons.request.ForgetPasswordRequest;
import com.coupons.request.LoginRequest;
import com.coupons.request.OtpRequest;
import com.coupons.request.OtpVerificationRequest;
import com.coupons.response.AuthResponse;
import com.coupons.services.CustomUserDetails;
import com.coupons.services.EmailServiceImpl;
import com.coupons.services.AuthTokenServiceImpl;
// import com.coupons.services.EmailService;
import com.coupons.services.SendOtpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @Autowired
    private AuthTokenServiceImpl authTokenService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sentOtp(@RequestBody OtpRequest request) throws UserException, EmailServiceException {

        String email = request.getEmail();
        email = email.trim();
        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }

        // Otp Verification
        String otp = sendOtpService.generateAndSaveOTP(email);

        emailService.sendEmailWithOTP(email, otp);

        return ResponseEntity.ok("Otp sent on email !!");

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) throws OTPException {

        boolean isValid = sendOtpService.verifyOTP(request.getEmail(), request.getOtp());

        if (!isValid) {
            throw new OTPException("Invalid OTP");
        }

        return ResponseEntity.ok("Otp verified successfully !!");

    }

    @PostMapping("/siginup")
    public ResponseEntity<AuthResponse> createUserHandler(@Validated @RequestBody User user) throws UserException {

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        // Checking user already being saved in database or not
        User isEmailExist = userRepository.findByEmail(email);

        // email found
        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }

        // Verifing that the user email is being verified by email
        // Optional<OtpVerification> verifiedUser = otpRepository.findByEmailIgnoreCase(email);

        // if (verifiedUser.isEmpty() || !verifiedUser.get().getIsUsed()) {
        //     throw new UserException("Email verification is required before signing up");
        // }

        // creating user and saved to database
        User creatUser = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .createdAt(LocalDateTime.now()) 
                .role(UserRole.USER) 
                .accountStatus(AccountStatus.ACTIVE)
                .isDeleted(false)
                .isverified(true)
                .build();

        userRepository.save(creatUser);

        // Getting User Roles
        Collection<? extends GrantedAuthority> authorities = creatUser.getRole().getAuthorities();

        // Saving creadentials to Security Context
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generating Token and Refresh Token
        String token = jwtProvider.generateToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        var tok = UserToken.builder()
                .user(creatUser)
                .token(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tok);

        // Generating and Sending Authentication Response.
        AuthResponse auth = new AuthResponse(token, refreshToken, creatUser.isIsverified());
        System.out.println("success" + auth.getJwt());

        return new ResponseEntity<AuthResponse>(auth, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signinHandler(@RequestBody LoginRequest loginRequest) throws UserException {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {

            // Authenticate User and Password
            Authentication authentication = authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // finding the user
            User user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UserException("Invalid email or password");
            }

            if (!user.isIsverified()) {
                throw new UserException("Email verification is required to sign in");
            }

            // Generating Token 
            String token = jwtProvider.generateToken(authentication);
            String refreshToken = jwtProvider.generateRefreshToken(authentication);

            var tok = UserToken.builder()
                    .user(user)
                    .token(refreshToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();

            tokenRepository.save(tok);

            AuthResponse auth = new AuthResponse();
            auth.setJwt(token);
            auth.setRefreshJwt(refreshToken);
            auth.setStatus(true);
            return new ResponseEntity<>(auth, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            throw new UserException("Invalid email or password");
        } finally {
            // Clear SecurityContext to avoid lasting a long time authentication data
            SecurityContextHolder.clearContext();
        }
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws TokenException, IOException {


        AuthResponse authResponse = authTokenService.refreshToken(request, response);

        if (authResponse == null) {
            throw new TokenException("Something went wrong please try again !!");
        }

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws TokenException{

        String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("No token provided");
        }
        jwt = authHeader;

        try {
            User user = userRepository.findByEmail(jwtProvider.getEmailFromToken(jwt));
            if (user != null) {
                authTokenService.revokeAllUserTokens(user);
                SecurityContextHolder.clearContext();
                return ResponseEntity.ok("Logout successful");

            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            throw new TokenException("Cannot find User or Tokens");
        }
    }

    @PatchMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordRequest request) throws UserException{

        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        String email = request.getEmail();

        User user = userRepository.findByEmail(email);

        if(user==null){
            throw new UserException("Invalid email user not found !");
        }

        // boolean isvalid = verifyOldPassword(oldPassword,user);

        if(!verifyOldPassword(oldPassword,user)){

            throw new UserException("New Password does not matches the old one !");
            
        }
        user.setPassword(passwordEncoder.encode(newPassword)); 

        userRepository.save(user);

        return ResponseEntity.ok("Password change successfully!!");
    }

    public boolean verifyOldPassword(String oldPassword,User user){

        if(passwordEncoder.matches(oldPassword,user.getPassword())){

            return true;
        }

        return false;
    }
}
