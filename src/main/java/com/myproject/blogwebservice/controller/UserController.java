package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.response.UserResponseDto;
import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.mapper.UserMapper;
import com.myproject.blogwebservice.service.abstraction.AvatarService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("${api-prefix}/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final AvatarService avatarService;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Operation(summary = "Get current user", description = "Returns currently authenticated user")
    @GetMapping("/current")
    public ResponseEntity<UserResponseDto> getCurrent(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AppUser currentUser = userService.getByUsername(authentication.getName());

        return ResponseEntity.ok(userMapper.mapToUserResponseDto(currentUser));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<Void> uploadAvatar(@RequestParam("file") MultipartFile file, Authentication authentication) {

        if (file == null || file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        avatarService.uploadAvatar(authentication.getName(), file);

        return ResponseEntity.ok().build();

    }

}
