package com.myproject.blogwebservice.validation.annotation.post;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank(message = "Post title is blank.")
@Size(max = 99, message = "Title can't be longer than 99 characters.")

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface TitleConstraints {

    String message() default "Invalid title.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
