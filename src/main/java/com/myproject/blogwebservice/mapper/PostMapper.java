package com.myproject.blogwebservice.mapper;

import com.myproject.blogwebservice.dto.request.PostRequestDto;
import com.myproject.blogwebservice.dto.response.PostResponseDto;
import com.myproject.blogwebservice.entity.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    PostResponseDto mapToPostResponseDto(Post post);

    Post mapToPost(PostRequestDto postRequestDto);

}
