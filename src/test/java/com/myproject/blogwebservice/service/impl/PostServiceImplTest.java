package com.myproject.blogwebservice.service.impl;

import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.exception.ResourceNotFoundException;
import com.myproject.blogwebservice.repository.PostRepository;
import com.myproject.blogwebservice.service.abstraction.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PostServiceImpl.class)
class PostServiceImplTest {

    @MockBean
    PostRepository postRepository;

    @Autowired
    PostService postService;


    private static final UUID id = UUID.randomUUID();
    private static final Post post = new Post();


    @BeforeAll
    static void init() {
        post.setId(id);
        post.setTitle("someTitle");
        post.setArticle("someArticle");
    }

    @Test
    void getById_shouldReturnPost_whenPostWasFound() {

        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        assertEquals(post, postService.getById(id));

        verify(postRepository).findById(id);
    }

    @Test
    void getById_shouldThrowException_whenPostWasNotFound() {

        when(postRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getById(id));

        verify(postRepository).findById(id);
    }

    @Test
    void isPostOfUser_shouldReturnTrue_whenPostBelongsToUser() {

        String username = "username";

        when(postRepository.existsByIdAndUserUsername(id, username)).thenReturn(true);

        assertTrue(postService.isPostOfUser(id, username));

        verify(postRepository).existsByIdAndUserUsername(id, username);
    }

    @Test
    void isPostOfUser_shouldReturnFalse_whenPostDoesNotBelongToUser() {

        String username = "username";

        when(postRepository.existsByIdAndUserUsername(id, username)).thenReturn(false);

        assertFalse(postService.isPostOfUser(id, username));

        verify(postRepository).existsByIdAndUserUsername(id, username);
    }

    @Test
    void create_shouldSavePostWithPublicationDateTime() {

        LocalDateTime beforeSave = LocalDateTime.now().minusHours(1);

        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            assertNotNull(savedPost.getPublicationDateTime());
            assertTrue(savedPost.getPublicationDateTime().isAfter(beforeSave));
            return savedPost;
        });

        postService.create(post);

        verify(postRepository).save(post);
    }

    @Test
    void delete_shouldDeletePostIfExists() {

        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        postService.delete(id);

        verify(postRepository).findById(id);
        verify(postRepository).deleteById(id);
    }

    @Test
    void delete_shouldThrowException_whenPostToDeleteNotFound() {

        when(postRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.delete(id));

        verify(postRepository).findById(id);
    }
}