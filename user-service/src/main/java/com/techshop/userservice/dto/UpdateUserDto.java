package com.techshop.userservice.dto;

import com.techshop.userservice.validation.annotation.UniqueEmail;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String address;
    private String imgUrl;
    private Long roleId;
    private String dateOfBirth;

    @Email
    @UniqueEmail
    private String email;
}
