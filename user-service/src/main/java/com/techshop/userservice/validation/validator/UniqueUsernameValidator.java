package com.techshop.userservice.validation.validator;

import com.techshop.userservice.common.util.ValidatorUtils;
import com.techshop.userservice.services.UserServices;
import com.techshop.userservice.validation.annotation.UniqueUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private String message;
    private UserServices service;

    public UniqueUsernameValidator(UserServices userService) {
        service = userService;
    }

    @Override
    public void initialize(UniqueUsername uniqueUsername) {
        message = uniqueUsername.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if(username == null)
            return false;

        boolean isTaken = service.isTakenUsername(username);

        if(!isTaken)
            return true;

        ValidatorUtils.addError(context, message);
        return false;
    }

}