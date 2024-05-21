package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.exception.ResourceNotFoundException;
import com.myproject.blogwebservice.repository.PostRepository;
import com.myproject.blogwebservice.service.abstraction.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    @Override
    public List<Post> getAll() {

        log.debug("Returning Post list");

        return postRepository.findAll();
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        log.debug("Returning Post page");

        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getAllByUserId(UUID id, Pageable pageable) {
        log.debug("Returning Post page by User ID: {}", id);

        return postRepository.findAllByUserId(id, pageable);
    }

    @Override
    public Post getById(UUID id) {
        log.debug("Looking for Post with ID: {}", id);

        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));
    }

    @Override
    public boolean isPostOfUser(UUID id, String username) {
        log.debug("Checking if Post with ID {} belongs to User with username: {}", id, username);

        return postRepository.existsByIdAndUserUsername(id, username);
    }

    @Override
    public Post create(Post post) {

        post.setPublicationDateTime(LocalDateTime.now());

        log.debug("Saving new Post: {}", post);

        return postRepository.save(post);
    }

    @Override
    public void delete(UUID id) {

        log.debug("Looking for Post to delete with ID: {}", id);

        postRepository.findById(id)
                .ifPresentOrElse(
                        post -> postRepository.deleteById(id),
                        () -> { throw new ResourceNotFoundException("Not found Post to delete with ID:" + id); }
                );
    }
}
