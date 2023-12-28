package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.repository.PostRepository;
import com.myproject.blogwebservice.service.abstraction.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
