package com.myproject.blogwebservice.exception.exceptionhandler;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundError(ResourceNotFoundException ex) {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleUnauthorizedError() {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), "Wrong credentials!"), HttpStatus.UNAUTHORIZED);
    }

}
