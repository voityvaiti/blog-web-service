package com.myproject.blogwebservice.service.abstraction;

public interface AuthService {

    String generateTokenByCredentials(String username, String password);

}
