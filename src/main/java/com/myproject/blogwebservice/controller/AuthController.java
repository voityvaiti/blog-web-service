package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.JwtRequestDto;
import com.myproject.blogwebservice.dto.JwtResponseDto;
import com.myproject.blogwebservice.dto.SignUpDto;
import com.myproject.blogwebservice.exception.UserDuplicationException;
import com.myproject.blogwebservice.service.abstraction.AuthService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import com.myproject.blogwebservice.validation.validator.UserUniquenessValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final UserService userService;

    private final UserUniquenessValidator userUniquenessValidator;


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> logIn(@RequestBody JwtRequestDto jwtRequestDto) {

        String token = authService.generateTokenByCredentials(jwtRequestDto.getUsername(), jwtRequestDto.getPassword());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDto> signUp(@RequestBody @Valid SignUpDto signUpDto) throws UserDuplicationException {

        userUniquenessValidator.validate(signUpDto.toAppUser());

        userService.createUser(signUpDto.toAppUser());

        String token = authService.generateTokenByCredentials(signUpDto.getUsername(), signUpDto.getPassword());
        return new ResponseEntity<>(new JwtResponseDto(token), HttpStatus.CREATED);
    }

}
