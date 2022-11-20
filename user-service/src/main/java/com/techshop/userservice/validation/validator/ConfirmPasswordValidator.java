package com.techshop.userservice.validation.validator;

import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.common.util.ValidatorUtils;
import com.techshop.userservice.validation.annotation.ConfirmPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class ConfirmPasswordValidator
        implements ConstraintValidator<ConfirmPassword, RegisterDto> {
    private String message;

    @Override
    public void initialize(ConfirmPassword constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RegisterDto dto, ConstraintValidatorContext context) {
        if(dto.getPassword() == null || dto.getConfirmPassword() == null) {
            ValidatorUtils.addError(context, message);
            return false;
        }

        if(dto.getPassword().equals(dto.getConfirmPassword()) )
            return true;

        ValidatorUtils.addError(context, message);
        return false;
    }

}
