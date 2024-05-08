package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.response.UserResponseDto;
import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.mapper.UserMapper;
import com.myproject.blogwebservice.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Value("${api-prefix}")
    private String apiPrefix;


    private static final String USERNAME = "testUser";
    private static final AppUser currentUser = new AppUser();
    private static final UserResponseDto userResponseDto = new UserResponseDto();
    private static Authentication mockAuthentication;



    @BeforeAll
    static void init() {
        currentUser.setUsername(USERNAME);
        userResponseDto.setUsername(USERNAME);

        mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getName()).thenReturn(currentUser.getUsername());
    }

    @Test
    void getCurrent_shouldReturnCurrentUser() throws Exception {

        when(userService.getByUsername(USERNAME)).thenReturn(currentUser);
        when(userMapper.mapToUserResponseDto(currentUser)).thenReturn(userResponseDto);

        mockMvc.perform(get(apiPrefix + "/users/current")
                        .principal(mockAuthentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME));
    }

    @Test
    void getCurrent_shouldGetCurrentUserByUsername() throws Exception {

        when(userService.getByUsername(USERNAME)).thenReturn(currentUser);
        when(userMapper.mapToUserResponseDto(currentUser)).thenReturn(userResponseDto);

        mockMvc.perform(get(apiPrefix + "/users/current")
                        .principal(mockAuthentication));

        verify(userService).getByUsername(USERNAME);
    }

    @Test
    void getCurrent_shouldReturnUnauthorizedIfNotAuthenticated() throws Exception {

        mockMvc.perform(get(apiPrefix + "/users/current"))
                .andExpect(status().isUnauthorized());
    }
}