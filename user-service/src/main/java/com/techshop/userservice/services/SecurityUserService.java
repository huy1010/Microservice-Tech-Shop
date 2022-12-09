package com.techshop.userservice.services;

import com.techshop.userservice.dto.ForgotPasswordDto;
import com.techshop.userservice.entity.PasswordResetToken;
import com.techshop.userservice.entity.User;
import com.techshop.userservice.entity.VerificationToken;

public interface SecurityUserService {
//    String validatePasswordResetToken(String username, String token);
//
//    String getForgotPasswordToken(String email);
    VerificationToken createVerificationToken(User user);
    Boolean verifyMailToken(String VerificationToken);
    PasswordResetToken createPasswordResetToken(String email);
    void verifyPasswordResetToken(ForgotPasswordDto dto);

}
