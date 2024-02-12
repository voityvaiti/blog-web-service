package com.myproject.blogwebservice.dto;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.validation.annotation.NicknameConstraints;
import com.myproject.blogwebservice.validation.annotation.PasswordConstraints;
import com.myproject.blogwebservice.validation.annotation.UsernameConstraints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    @UsernameConstraints
    private String username;

    @PasswordConstraints
    private String password;

    @NicknameConstraints
    private String nickname;

    public AppUser toAppUser() {
        AppUser user = new AppUser();

        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setNickname(this.nickname);

        return user;
    }

}
