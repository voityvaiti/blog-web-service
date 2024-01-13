package com.myproject.blogwebservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(article, post.article) && Objects.equals(publicationDateTime, post.publicationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, article, publicationDateTime);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", article='" + article + '\'' +
                ", publicationDateTime=" + publicationDateTime +
                '}';
    }
}
