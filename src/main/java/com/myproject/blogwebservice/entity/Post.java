package com.myproject.blogwebservice.entity;

import com.myproject.blogwebservice.validation.annotation.post.ArticleConstraints;
import com.myproject.blogwebservice.validation.annotation.post.TitleConstraints;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;


    @CreatedDate
    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

}
