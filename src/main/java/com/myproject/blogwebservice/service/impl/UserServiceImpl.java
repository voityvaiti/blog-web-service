package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Role;
import com.myproject.blogwebservice.repository.UserRepository;
import com.myproject.blogwebservice.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public boolean existsWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public AppUser createUser(AppUser user) {

        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public AppUser createAdmin(AppUser user) {

        user.setRole(Role.ADMIN);

        return userRepository.save(user);
    }
}
