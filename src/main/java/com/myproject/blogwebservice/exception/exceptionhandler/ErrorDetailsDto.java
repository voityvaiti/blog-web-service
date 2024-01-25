package com.myproject.blogwebservice.exception.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetailsDto {

    private LocalDateTime timestamp;

    private Object info;
}
