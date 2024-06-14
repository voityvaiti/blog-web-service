package com.myproject.blogwebservice.exception.exceptionhandler;

import com.myproject.blogwebservice.exception.CloudException;
import com.myproject.blogwebservice.exception.ResourceNotFoundException;
import com.myproject.blogwebservice.exception.UserDuplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, UserDuplicationException.class})
    public ResponseEntity<Object> handleValidationException(BindException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException() {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), "Wrong credentials!"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CloudException.class)
    public ResponseEntity<Object> handleCloudUploadingException(CloudException ex) {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
