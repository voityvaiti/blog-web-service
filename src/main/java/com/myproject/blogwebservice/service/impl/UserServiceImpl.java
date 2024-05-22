package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Role;
import com.myproject.blogwebservice.exception.ResourceNotFoundException;
import com.myproject.blogwebservice.repository.UserRepository;
import com.myproject.blogwebservice.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean existsWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public AppUser getByUsername(String username) {
        log.debug("Looking for User with username: {}", username);

        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }


    public AppUser create(AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.debug("Saving new User: {}", user);
        return userRepository.save(user);
    }

    @Override
    public AppUser createUser(AppUser user) {

        user.setRole(Role.USER);
        return create(user);
    }

    @Override
    public AppUser createAdmin(AppUser user) {

        user.setRole(Role.ADMIN);
        return create(user);
    }
}
