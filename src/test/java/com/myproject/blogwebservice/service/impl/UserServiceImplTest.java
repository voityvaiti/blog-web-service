package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Role;
import com.myproject.blogwebservice.exception.ResourceNotFoundException;
import com.myproject.blogwebservice.repository.UserRepository;
import com.myproject.blogwebservice.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;


    private static final String username = "someUsername";
    private static final AppUser user = new AppUser();

    @BeforeAll
    static void init() {
        user.setUsername(username);
        user.setPassword("somePassword");
        user.setRole(Role.USER);
    }


    @Test
    void existsWithUsername_shouldReturnTrue_whenUsernameExists() {
        String username = "testuser";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertTrue(userService.existsWithUsername(username));

        verify(userRepository).existsByUsername(username);
    }

    @Test
    void existsWithUsername_shouldReturnFalse_whenUsernameDoesNotExist() {

        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertFalse(userService.existsWithUsername(username));

        verify(userRepository).existsByUsername(username);
    }

    @Test
    void getByUsername_shouldReturnUser_whenUserExists() {

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertEquals(user, userService.getByUsername(username));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void getByUsername_shouldThrowException_whenUserDoesNotExist() {

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByUsername(username));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void createUser_shouldCreateUserWithUserRole() {

        String rawPassword = "rawPassword";
        String encodedPassword = "encodedPassword";

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(rawPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);

        AppUser createdUser = userService.createUser(user);

        assertEquals(Role.USER, createdUser.getRole());
        assertEquals(encodedPassword, createdUser.getPassword());

        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(user);
    }

    @Test
    void createAdmin_shouldCreateUserWithAdminRole() {

        String rawPassword = "rawPassword";
        String encodedPassword = "encodedPassword";

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(rawPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);

        AppUser createdAdmin = userService.createAdmin(user);

        assertEquals(Role.ADMIN, createdAdmin.getRole());
        assertEquals(encodedPassword, createdAdmin.getPassword());

        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(user);
    }
}