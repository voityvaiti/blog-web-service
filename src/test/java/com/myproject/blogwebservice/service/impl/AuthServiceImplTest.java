package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.security.JwtTokenUtil;
import com.myproject.blogwebservice.service.abstraction.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthServiceImpl.class)
class AuthServiceImplTest {

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Test
    void generateTokenByCredentials_shouldGenerateToken() {

        String username = "testuser";
        String password = "testpassword";
        UserDetails userDetails = new User(username, password, Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("generatedToken");

        String token = authService.generateTokenByCredentials(username, password);

        assertNotNull(token);
        assertEquals("generatedToken", token);

        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(jwtTokenUtil).generateToken(userDetails);
    }

}