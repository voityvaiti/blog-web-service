package com.myproject.blogwebservice.mapper;

import com.myproject.blogwebservice.entity.Avatar;
import com.myproject.blogwebservice.pojo.ImagePojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AvatarMapper {

    Avatar mapToAvatar(ImagePojo imagePojo);

    @Mapping(target = "publicId", source = "imagePojo.publicId")
    @Mapping(target = "url", source = "imagePojo.url")
    void updateAvatarFromImagePojo(ImagePojo imagePojo, @MappingTarget Avatar avatar);

}
