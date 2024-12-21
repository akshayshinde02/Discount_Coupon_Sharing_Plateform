package com.coupons.exceptions;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobleException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue, WebRequest req){
        ErrorDetails err = new ErrorDetails(ue.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CouponException.class)
    public ResponseEntity<ErrorDetails> CouponExceptionHandler(CouponException ce, WebRequest req){
        ErrorDetails err = new ErrorDetails(ce.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ErrorDetails> CategoryExceptionHandler(CategoryException ce, WebRequest req){
        ErrorDetails err = new ErrorDetails(ce.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OTPException.class)
    public ResponseEntity<ErrorDetails> OTPExceptionHandler(OTPException oe,WebRequest req){
        ErrorDetails err = new ErrorDetails(oe.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorDetails> tokenExceptionHandler(TokenException ee, WebRequest req){

        ErrorDetails err = new ErrorDetails(ee.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<ErrorDetails> emailServiceExceptionHandler(EmailServiceException ee, WebRequest req){

        ErrorDetails err = new ErrorDetails(ee.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ErrorDetails> cartExceptionHandler(CartException ee, WebRequest req){

        ErrorDetails err = new ErrorDetails(ee.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorDetails> orderExceptionHandler(OrderException ee, WebRequest req){

        ErrorDetails err = new ErrorDetails(ee.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDetails> invalidTokenExceptionHandler(InvalidTokenException ee, WebRequest req){

        ErrorDetails err = new ErrorDetails(ee.getMessage(),"unauthorized!",LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> CategoryExceptionHandler(NoHandlerFoundException ce, HttpHeaders headers, HttpStatus status,  WebRequest req){
        
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("message", "Endpoint not found");
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(UsernameNotFoundException ce, WebRequest req){
        ErrorDetails err = new ErrorDetails(ce.getMessage(),"Unauthorized",LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception ce, WebRequest req){
        ErrorDetails err = new ErrorDetails(ce.getMessage(),"Unauthorized",LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.ACCEPTED);
    }
}
