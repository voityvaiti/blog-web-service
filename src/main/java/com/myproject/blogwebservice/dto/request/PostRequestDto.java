package com.myproject.blogwebservice.dto.request;

import com.myproject.blogwebservice.validation.annotation.post.ArticleConstraints;
import com.myproject.blogwebservice.validation.annotation.post.TitleConstraints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    @TitleConstraints
    private String title;

    @ArticleConstraints
    private String article;

}
