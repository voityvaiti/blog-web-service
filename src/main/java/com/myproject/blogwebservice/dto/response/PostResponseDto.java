package com.myproject.blogwebservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    private UUID id;

    private String title;

    private String article;

    private LocalDateTime publicationDateTime;

    private UserResponseDto user;

}
