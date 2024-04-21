package com.myproject.blogwebservice.entity;

import com.myproject.blogwebservice.validation.annotation.post.ArticleConstraints;
import com.myproject.blogwebservice.validation.annotation.post.TitleConstraints;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    @TitleConstraints
    private String title;

    @Column(name = "article")
    @ArticleConstraints
    private String article;

    @Column(name = "publication_datetime")
    private LocalDateTime publicationDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

}
