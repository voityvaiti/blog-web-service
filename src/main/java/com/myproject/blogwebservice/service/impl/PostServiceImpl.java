package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.exception.ResourceNotFoundException;
import com.myproject.blogwebservice.repository.PostRepository;
import com.myproject.blogwebservice.service.abstraction.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Override
    public Post getById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));
    }

    @Override
    public Post create(Post post) {

        post.setPublicationDateTime(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public void delete(UUID id) {

        postRepository.findById(id)
                .ifPresentOrElse(
                        post -> postRepository.deleteById(id),
                        () -> { throw new ResourceNotFoundException("Not found Post to delete with ID:" + id); }
                );
    }
}
