package com.myproject.blogwebservice.service.abstraction;

import com.myproject.blogwebservice.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAll();

    Post getById(UUID id);

    Post create(Post post);
}
