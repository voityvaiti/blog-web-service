package com.myproject.blogwebservice.validation.validator;

import com.myproject.blogwebservice.entity.AppUser;
import com.myproject.blogwebservice.exception.UserDuplicationException;
import com.myproject.blogwebservice.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

@Component
@RequiredArgsConstructor
public class UserUniquenessValidator {

    private final UserService userService;


    public void validate(AppUser user) throws UserDuplicationException {

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(user, "user");

        validateUsername(user, result);

        if(result.hasErrors()) {
            throw new UserDuplicationException(result);
        }
    }

    private void validateUsername(AppUser user, BeanPropertyBindingResult result) {

        if (userService.existsWithUsername(user.getUsername())) {
            result.rejectValue("username", "user.username", "User with same username is already exists.");
        }

    }
}
