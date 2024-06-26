package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.dto.request.PostRequestDto;
import com.myproject.blogwebservice.dto.response.PostResponseDto;
import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.mapper.PostMapper;
import com.myproject.blogwebservice.service.abstraction.PostService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api-prefix}/posts")
@RequiredArgsConstructor
@Tag(name = "Post")
public class PostController {

    protected static final Sort DEFAULT_SORT = Sort.by("createdDate").descending();


    private final PostService postService;
    private final UserService userService;

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);


    @Operation(summary = "Get all posts page", description = "Returns page of posts")
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPage(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                            @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize) {

        Page<Post> postPage = postService.getAll(PageRequest.of(pageNumber, pageSize, DEFAULT_SORT));

        return ResponseEntity.ok(postPage.map(postMapper::mapToPostResponseDto));
    }

    @Operation(summary = "Get all posts page by auth", description = "Returns posts page of currently authenticated user")
    @GetMapping("/current-user")
    public ResponseEntity<Page<PostResponseDto>> getAllByAuth(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize,
                                                   Authentication authentication) {

        AppUser user = userService.getByUsername(authentication.getName());

        Page<Post> postPage = postService.getAllByUserId(user.getId(), PageRequest.of(pageNumber, pageSize, DEFAULT_SORT));

        return ResponseEntity.ok(postPage.map(postMapper::mapToPostResponseDto));
    }

    @Operation(summary = "Get post by ID", description = "Returns posts with specific ID")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> get(@PathVariable UUID id) {

        Post post = postService.getById(id);

        return ResponseEntity.ok(postMapper.mapToPostResponseDto(post));
    }

    @Operation(summary = "Create post", description = "Creates post and wires currently authenticated user as creator")
    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody @Valid PostRequestDto postRequestDto, Authentication authentication) {

        Post post = postMapper.mapToPost(postRequestDto);

        post.setUser(userService.getByUsername(authentication.getName()));
        Post createdPost = postService.create(post);

        return new ResponseEntity<>(postMapper.mapToPostResponseDto(createdPost), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete post", description = "Deletes post only if currently authenticated user is creator")
    @DeleteMapping("/current-user/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, Authentication authentication) {

        if(!postService.isPostOfUser(id, authentication.getName())) {
            throw new AccessDeniedException("Can't delete post of another user.");
        }
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
