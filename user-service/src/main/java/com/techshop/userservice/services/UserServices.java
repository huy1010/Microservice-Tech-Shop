package com.techshop.userservice.services;

import com.techshop.userservice.dto.ChangePasswordDto;
import com.techshop.userservice.dto.LoginDto;
import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.dto.UpdateUserDto;
import com.techshop.userservice.entity.User;

public interface UserServices {
    String login(LoginDto user);
    User register(RegisterDto user);
    User getProfile(String username);
    User updateUser(UpdateUserDto dto);
    void changePassword(String username, ChangePasswordDto dto);
    boolean isTakenEmail(String email);
    boolean isTakenUsername(String userName);
}
