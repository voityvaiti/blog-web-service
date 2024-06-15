package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Avatar;
import com.myproject.blogwebservice.mapper.AvatarMapper;
import com.myproject.blogwebservice.pojo.ImagePojo;
import com.myproject.blogwebservice.repository.AvatarRepository;
import com.myproject.blogwebservice.service.abstraction.AvatarService;
import com.myproject.blogwebservice.service.abstraction.CloudinaryService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private static final String AVATAR_CLOUD_FOLDER = "avatars";


    private final AvatarMapper avatarMapper = Mappers.getMapper(AvatarMapper.class);

    private final AvatarRepository avatarRepository;

    private final UserService userService;
    private final CloudinaryService cloudinaryService;


    @Override
    public void uploadAvatar(String username, MultipartFile avatarFile) {

        AppUser user = userService.getByUsername(username);
        ImagePojo imagePojo = cloudinaryService.uploadFile(avatarFile, AVATAR_CLOUD_FOLDER);

        Avatar avatar;

        if (user.getAvatar() != null) {

            avatar = user.getAvatar();

            cloudinaryService.deleteFile(avatar.getPublicId(), AVATAR_CLOUD_FOLDER);

            avatarMapper.updateAvatarFromImagePojo(imagePojo, avatar);

        } else {
            avatar = avatarMapper.mapToAvatar(imagePojo);
            avatar.setUser(user);
        }

        avatarRepository.save(avatar);
    }
}
