package com.techshop.userservice.validation.validator;

import com.techshop.userservice.common.util.ValidatorUtils;
import com.techshop.userservice.services.UserServices;
import com.techshop.userservice.validation.annotation.UniqueEmail;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class UniqueEmailValidator  implements ConstraintValidator<UniqueEmail, String> {
    private String message;
    private UserServices service;

    public UniqueEmailValidator(UserServices userService) {
        service = userService;
    }

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
        message = uniqueEmail.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null)
            return false;

        boolean isTaken = service.isTakenEmail(email);

        if(!isTaken)
            return true;

        ValidatorUtils.addError(context, message);
        return false;
    }

}