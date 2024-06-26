package com.myproject.blogwebservice.repository;

import com.myproject.blogwebservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findAllByUserId(UUID id, Pageable pageable);

    boolean existsByIdAndUserUsername(UUID id, String username);

}
