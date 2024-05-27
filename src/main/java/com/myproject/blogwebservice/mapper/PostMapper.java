package com.myproject.blogwebservice.mapper;

import com.myproject.blogwebservice.dto.request.PostRequestDto;
import com.myproject.blogwebservice.dto.response.PostResponseDto;
import com.myproject.blogwebservice.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper
public interface PostMapper {

    @Mapping(source = "createdDate", target = "publicationDateTime")
    PostResponseDto mapToPostResponseDto(Post post);

    Post mapToPost(PostRequestDto postRequestDto);


    default LocalDateTime map(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null
                ? zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                : null;
    }

}
