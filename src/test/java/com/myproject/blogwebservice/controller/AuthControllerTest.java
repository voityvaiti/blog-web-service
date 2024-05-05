package com.myproject.blogwebservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.blogwebservice.dto.request.JwtRequestDto;
import com.myproject.blogwebservice.dto.request.SignUpRequestDto;
import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.exception.UserDuplicationException;
import com.myproject.blogwebservice.mapper.UserMapper;
import com.myproject.blogwebservice.service.abstraction.AuthService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import com.myproject.blogwebservice.validation.validator.UserUniquenessValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserUniquenessValidator userUniquenessValidator;

    @MockBean
    private UserMapper userMapper;

    @Value("${api-prefix}")
    private String apiPrefix;


    private static final String token = "mock-token";
    private static final AppUser user = new AppUser();
    private static final JwtRequestDto jwtRequestDto = new JwtRequestDto();
    private static final SignUpRequestDto signUpRequestDto = new SignUpRequestDto();


    @BeforeAll
    static void init() {
        user.setUsername("someUsername");
        user.setPassword("somePassword");
        user.setNickname("someNickname");

        jwtRequestDto.setUsername(user.getUsername());
        jwtRequestDto.setPassword(user.getPassword());

        signUpRequestDto.setUsername(user.getUsername());
        signUpRequestDto.setPassword(user.getPassword());
        signUpRequestDto.setNickname(user.getNickname());
    }

    @Test
    void logIn_shouldReturnJwtResponseDto_whenCredentialsAreValid() throws Exception {

        when(authService.generateTokenByCredentials(jwtRequestDto.getUsername(), jwtRequestDto.getPassword())).thenReturn(token);

        mockMvc.perform(post(apiPrefix + "/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void logIn_shouldGenerateTokenByCredentials_whenCredentialsAreValid() throws Exception {

        when(authService.generateTokenByCredentials(jwtRequestDto.getUsername(), jwtRequestDto.getPassword())).thenReturn(token);

        mockMvc.perform(post(apiPrefix + "/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequestDto)));

        verify(authService).generateTokenByCredentials(jwtRequestDto.getUsername(), jwtRequestDto.getPassword());
    }

    @Test
    void signUp_shouldReturnJwtResponseDto_whenSignUpRequestIsValid() throws Exception {

        when(userMapper.mapToUser(signUpRequestDto)).thenReturn(user);
        when(authService.generateTokenByCredentials(signUpRequestDto.getUsername(), signUpRequestDto.getPassword())).thenReturn(token);

        mockMvc.perform(post(apiPrefix + "/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void signUp_shouldValidateAndCreateUser_whenSignUpRequestIsValid() throws Exception {

        when(userMapper.mapToUser(signUpRequestDto)).thenReturn(user);
        when(authService.generateTokenByCredentials(signUpRequestDto.getUsername(), signUpRequestDto.getPassword())).thenReturn(token);

        mockMvc.perform(post(apiPrefix + "/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequestDto)));

        verify(userUniquenessValidator).validate(user);
        verify(userService).createUser(user);
    }

    @Test
    void signUp_shouldReturnBadRequest_whenUserDuplicationExceptionIsThrown() throws Exception {

        when(userMapper.mapToUser(signUpRequestDto)).thenReturn(new AppUser());
        doThrow(UserDuplicationException.class).when(userUniquenessValidator).validate(any(AppUser.class));

        mockMvc.perform(post(apiPrefix + "/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequestDto)))
                .andExpect(status().isBadRequest());
    }
}