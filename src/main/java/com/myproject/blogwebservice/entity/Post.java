package com.myproject.blogwebservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Post title is blank.")
    @Size(max = 99, message = "Title can't be longer than 99 characters.")
    private String title;

    @Column(name = "article")
    @NotBlank(message = "Post article is blank.")
    @Size(max = 999, message = "Article can't be longer than 999 characters.")
    private String article;

    @Column(name = "publication_datetime")
    private LocalDateTime publicationDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private AppUser user;

}
