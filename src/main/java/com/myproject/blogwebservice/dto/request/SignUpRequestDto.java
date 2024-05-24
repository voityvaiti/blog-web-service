package com.myproject.blogwebservice.dto.request;

import com.myproject.blogwebservice.validation.annotation.user.NicknameConstraints;
import com.myproject.blogwebservice.validation.annotation.user.PasswordConstraints;
import com.myproject.blogwebservice.validation.annotation.user.UsernameConstraints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @UsernameConstraints
    private String username;

    @PasswordConstraints
    private String password;

    @NicknameConstraints
    private String nickname;

}
