package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.security.JwtTokenUtil;
import com.myproject.blogwebservice.service.abstraction.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;


    @Override
    public String generateTokenByCredentials(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
    }
}
