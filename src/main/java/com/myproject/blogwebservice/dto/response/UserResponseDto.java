package com.myproject.blogwebservice.dto.response;

import com.myproject.blogwebservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private UUID id;

    private String username;

    private Role role;

    private String nickname;

    private String avatarUrl;

}
