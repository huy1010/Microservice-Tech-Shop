package com.techshop.userservice.validation.annotation;

import com.techshop.userservice.validation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RUNTIME)
@Target(FIELD)
public @interface UniqueEmail {
    String message() default "Email has been used.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
