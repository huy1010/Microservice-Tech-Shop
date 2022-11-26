package com.techshop.userservice.dto;

import com.techshop.userservice.validation.annotation.FieldsValueMatch;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@FieldsValueMatch(
        field = "newPassword",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match!"
)
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
