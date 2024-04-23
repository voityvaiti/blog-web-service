package com.myproject.blogwebservice.mapper;

import com.myproject.blogwebservice.dto.request.SignUpRequestDto;
import com.myproject.blogwebservice.dto.response.UserResponseDto;
import com.myproject.blogwebservice.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponseDto mapToUserResponseDto(AppUser user);

    AppUser mapToUser(SignUpRequestDto signUpRequestDto);

}
