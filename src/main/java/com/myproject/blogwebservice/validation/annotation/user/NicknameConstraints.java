package com.myproject.blogwebservice.validation.annotation.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank(message = "Nickname is required.")
@Size(max = 30, message = "Nickname cannot be longer then 30 characters.")

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface NicknameConstraints {

    String message() default "Invalid nickname.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
