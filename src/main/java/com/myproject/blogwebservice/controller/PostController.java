package com.myproject.blogwebservice.controller;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.entity.Post;
import com.myproject.blogwebservice.service.abstraction.PostService;
import com.myproject.blogwebservice.service.abstraction.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api-prefix}/posts")
@RequiredArgsConstructor
public class PostController {

    private static final String SORT_PROPERTY = "publicationDateTime";


    private final PostService postService;

    private final UserService userService;


    
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPage(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                 @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize) {

        return ResponseEntity.ok(postService.getAll(PageRequest.of(pageNumber, pageSize, Sort.by(SORT_PROPERTY).descending())));
    }

    @GetMapping("/current-user")
    public ResponseEntity<Page<Post>> getAllByAuth(@RequestParam(defaultValue = "0", name = "page-number") Integer pageNumber,
                                                   @RequestParam(defaultValue = "10", name = "page-size") Integer pageSize,
                                                   Authentication authentication) {

        AppUser user = userService.getByUsername(authentication.getName());

        return ResponseEntity.ok(postService.getAllByUserId(user.getId(), PageRequest.of(pageNumber, pageSize, Sort.by(SORT_PROPERTY).descending())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody @Valid Post post, Authentication authentication) {

        post.setUser(userService.getByUsername(authentication.getName()));

        Post createdPost = postService.create(post);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
