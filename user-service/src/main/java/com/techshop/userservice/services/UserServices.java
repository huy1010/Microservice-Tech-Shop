package com.techshop.userservice.services;

import com.techshop.userservice.dto.LoginDto;
import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.entity.User;

public interface UserServices {
    boolean login(LoginDto user);
    User register(RegisterDto user);
    boolean isTakenEmail(String email);
    boolean isTakenUsername(String userName);
}
