package com.myproject.blogwebservice.exception;


import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class UserDuplicationException extends BindException {
    public UserDuplicationException(BindingResult bindingResult) {
        super(bindingResult);
    }
}
