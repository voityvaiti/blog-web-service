package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.request.JwtRequestDto;
import com.myproject.blogwebservice.dto.response.JwtResponseDto;
import com.myproject.blogwebservice.dto.request.SignUpRequestDto;
import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.exception.UserDuplicationException;
import com.myproject.blogwebservice.mapper.UserMapper;
import com.myproject.blogwebservice.service.abstraction.AuthService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import com.myproject.blogwebservice.validation.validator.UserUniquenessValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
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

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> logIn(@RequestBody @Valid JwtRequestDto jwtRequestDto) {

        String token = authService.generateTokenByCredentials(jwtRequestDto.getUsername(), jwtRequestDto.getPassword());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) throws UserDuplicationException {

        AppUser userToRegister = userMapper.mapToUser(signUpRequestDto);
        userUniquenessValidator.validate(userToRegister);

        userService.createUser(userToRegister);

        String token = authService.generateTokenByCredentials(signUpRequestDto.getUsername(), signUpRequestDto.getPassword());
        return new ResponseEntity<>(new JwtResponseDto(token), HttpStatus.CREATED);
    }

}
