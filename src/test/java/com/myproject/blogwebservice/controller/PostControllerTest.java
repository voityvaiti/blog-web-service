package com.myproject.blogwebservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.blogwebservice.dto.request.PostRequestDto;
import com.myproject.blogwebservice.dto.response.PostResponseDto;
import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.mapper.PostMapper;
import com.myproject.blogwebservice.service.abstraction.PostService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.UUID;

import static com.myproject.blogwebservice.controller.PostController.DEFAULT_SORT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @MockBean
    private PostMapper postMapper;

    @Value("${api-prefix}")
    private String apiPrefix;


    private static final UUID postId = UUID.randomUUID();
    private static final AppUser user = new AppUser();
    private static final Post post = new Post();
    private static final PostRequestDto postRequestDto = new PostRequestDto();
    private static final PostResponseDto postResponseDto = new PostResponseDto();
    private static Authentication mockAuthentication;
    private static Page<Post> postPage;


    @BeforeAll
    static void init() {
        user.setId(UUID.randomUUID());
        user.setUsername("someUsername");

        post.setId(postId);
        post.setTitle("someTitle");
        post.setArticle("someArticle");

        postRequestDto.setTitle(post.getTitle());
        postRequestDto.setArticle(post.getArticle());

        postResponseDto.setId(postId);
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setArticle(post.getArticle());

        mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getName()).thenReturn(user.getUsername());

        postPage = new PageImpl<>(Collections.singletonList(post));
    }


    @Test
    void getAllPage_shouldReturnPostPage() throws Exception {

        when(postService.getAll(any(PageRequest.class))).thenReturn(postPage);
        when(postMapper.mapToPostResponseDto(any(Post.class))).thenReturn(postResponseDto);

        mockMvc.perform(get(apiPrefix + "/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(postId.toString()))
                .andExpect(jsonPath("$.content[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.content[0].article").value(post.getArticle()));
    }

    @Test
    void getAllPage_shouldGetPostsByProperPageRequest() throws Exception {

        int pageNumber = 7;
        int pageSize = 2;

        when(postService.getAll(any(PageRequest.class))).thenReturn(postPage);
        when(postMapper.mapToPostResponseDto(any(Post.class))).thenReturn(postResponseDto);

        mockMvc.perform(get(apiPrefix + "/posts")
                .param("page-number", String.valueOf(pageNumber))
                .param("page-size", String.valueOf(pageSize)));

        verify(postService).getAll(PageRequest.of(pageNumber, pageSize, DEFAULT_SORT));
    }

    @Test
    void getAllByAuth_shouldReturnPostPageForCurrentUser() throws Exception {

        when(userService.getByUsername(user.getUsername())).thenReturn(user);
        when(postService.getAllByUserId(any(UUID.class), any(PageRequest.class))).thenReturn(postPage);
        when(postMapper.mapToPostResponseDto(any(Post.class))).thenReturn(postResponseDto);

        mockMvc.perform(get(apiPrefix + "/posts/current-user")
                        .principal(mockAuthentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(postId.toString()))
                .andExpect(jsonPath("$.content[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.content[0].article").value(post.getArticle()));
    }

    @Test
    void getAllByAuth_shouldGetPostsByProperParams() throws Exception {

        int pageNumber = 5;
        int pageSize = 12;

        when(userService.getByUsername(user.getUsername())).thenReturn(user);
        when(postService.getAllByUserId(any(UUID.class), any(PageRequest.class))).thenReturn(postPage);
        when(postMapper.mapToPostResponseDto(any(Post.class))).thenReturn(postResponseDto);

        mockMvc.perform(get(apiPrefix + "/posts/current-user")
                        .principal(mockAuthentication)
                .param("page-number", String.valueOf(pageNumber))
                .param("page-size", String.valueOf(pageSize)));

        verify(postService).getAllByUserId(user.getId(), PageRequest.of(pageNumber, pageSize, DEFAULT_SORT));
    }

    @Test
    void get_shouldReturnPostById() throws Exception {

        when(postService.getById(postId)).thenReturn(post);
        when(postMapper.mapToPostResponseDto(any(Post.class))).thenReturn(postResponseDto);

        mockMvc.perform(get(apiPrefix + "/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId.toString()))
                .andExpect(jsonPath("$.title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("$.article").value(postResponseDto.getArticle()));
    }

    @Test
    void get_shouldGetPostByProperId() throws Exception {

        when(postService.getById(postId)).thenReturn(post);
        when(postMapper.mapToPostResponseDto(any(Post.class))).thenReturn(postResponseDto);

        mockMvc.perform(get(apiPrefix + "/posts/{id}", postId));

        verify(postService).getById(postId);
    }

    @Test
    void create_shouldReturnCreatedPost() throws Exception {

        when(postMapper.mapToPost(any(PostRequestDto.class))).thenReturn(post);
        when(userService.getByUsername(anyString())).thenReturn(user);
        when(postService.create(any(Post.class))).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix + "/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postRequestDto))
                        .principal(mockAuthentication))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(postId.toString()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.article").value(post.getArticle()));
    }

    @Test
    void create_shouldGetUserByCurrentAuth() throws Exception {

        when(postMapper.mapToPost(any(PostRequestDto.class))).thenReturn(post);
        when(userService.getByUsername(anyString())).thenReturn(user);
        when(postService.create(any(Post.class))).thenReturn(post);

        mockMvc.perform(MockMvcRequestBuilders.post(apiPrefix  + "/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postRequestDto))
                        .principal(mockAuthentication))
                .andExpect(status().isCreated());

        verify(userService).getByUsername(user.getUsername());


    }

    @Test
    void delete_shouldDeletePostIfCurrentUserIsOwner() throws Exception {

        when(postService.isPostOfUser(postId, user.getUsername())).thenReturn(true);

        mockMvc.perform(delete(apiPrefix + "/posts/current-user/{id}", postId)
                        .principal(mockAuthentication))
                .andExpect(status().isOk());

        verify(postService).delete(postId);
    }

    @Test
    void delete_shouldReturnForbiddenIfCurrentUserIsNotOwner() throws Exception {
        when(postService.isPostOfUser(postId, user.getUsername())).thenReturn(false);

        mockMvc.perform(delete(apiPrefix + "/posts/current-user/{id}", postId)
                        .principal(mockAuthentication))
                .andExpect(status().isForbidden());

        verify(postService, never()).delete(any());
    }
}