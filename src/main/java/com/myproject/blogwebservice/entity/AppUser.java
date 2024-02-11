package com.myproject.blogwebservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproject.blogwebservice.validation.annotation.NicknameConstraints;
import com.myproject.blogwebservice.validation.annotation.PasswordConstraints;
import com.myproject.blogwebservice.validation.annotation.UsernameConstraints;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usr")
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

}
