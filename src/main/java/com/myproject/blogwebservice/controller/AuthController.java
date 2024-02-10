package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.JwtRequestDto;
import com.myproject.blogwebservice.dto.JwtResponseDto;
import com.myproject.blogwebservice.service.abstraction.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api-prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping
    public ResponseEntity<JwtResponseDto> createAuthToken(@RequestBody JwtRequestDto jwtRequestDto) {

        String token = authService.generateTokenByCredentials(jwtRequestDto.getUsername(), jwtRequestDto.getPassword());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }

}
