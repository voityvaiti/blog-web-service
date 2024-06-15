package com.myproject.blogwebservice.service.abstraction;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    void uploadAvatar(String username, MultipartFile avatarFile);

}
