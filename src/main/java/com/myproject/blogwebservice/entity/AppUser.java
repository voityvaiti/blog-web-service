package com.myproject.blogwebservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproject.blogwebservice.validation.annotation.user.NicknameConstraints;
import com.myproject.blogwebservice.validation.annotation.user.PasswordConstraints;
import com.myproject.blogwebservice.validation.annotation.user.UsernameConstraints;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usr")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", unique = true)
    @UsernameConstraints
    private String username;

    @Column(name = "password")
    @ToString.Exclude
    @JsonIgnore
    @PasswordConstraints
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "nickname")
    @NicknameConstraints
    private String nickname;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();


    @CreatedDate
    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

}
