package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.JwtRequestDto;
import com.myproject.blogwebservice.dto.JwtResponseDto;
import com.myproject.blogwebservice.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api-prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;



    @PostMapping
    public ResponseEntity<JwtResponseDto> createAuthToken(@RequestBody JwtRequestDto jwtRequestDto) throws BadCredentialsException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDto.getUsername(), jwtRequestDto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequestDto.getUsername());

        return ResponseEntity.ok(new JwtResponseDto(jwtTokenUtil.generateToken(userDetails)));
    }

}
