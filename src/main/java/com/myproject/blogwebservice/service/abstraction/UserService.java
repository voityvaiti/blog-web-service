package com.myproject.blogwebservice.service.abstraction;

import com.myproject.blogwebservice.entity.AppUser;

public interface UserService {

    boolean existsWithUsername(String username);

    AppUser createUser(AppUser user);

    AppUser createAdmin(AppUser user);

}