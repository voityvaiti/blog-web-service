package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.service.abstraction.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAll();
    }
}
