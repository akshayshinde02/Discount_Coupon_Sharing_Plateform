package com.coupons.exceptions;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> CategoryExceptionHandler(NoHandlerFoundException ce, HttpHeaders headers, HttpStatus status,  WebRequest req){
        
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("message", "Endpoint not found");
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(CouponException ce, WebRequest req){
        ErrorDetails err = new ErrorDetails(ce.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err,HttpStatus.ACCEPTED);
    }
}
