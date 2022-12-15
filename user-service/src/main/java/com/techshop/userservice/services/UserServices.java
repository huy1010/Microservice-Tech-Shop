package com.techshop.userservice.services;

import com.techshop.userservice.dto.*;
import com.techshop.userservice.entity.User;

import java.util.List;

public interface UserServices {
    boolean login(LoginDto user);

    User getUserByName(String username);

    User register(RegisterDto user);

    User getProfile(String username);

    User updateUser(UpdateUserDto dto);

    void changePassword(String username, ChangePasswordDto dto);

    boolean isTakenEmail(String email);

    boolean isTakenUsername(String userName);

    List<User> getUsers();

    List<User> getCustomers();

    void deleteUserByUsername(String username);

    void changeStatus(BlockedUserDto dto);

    User createUser(RegisterDto dto);

}
