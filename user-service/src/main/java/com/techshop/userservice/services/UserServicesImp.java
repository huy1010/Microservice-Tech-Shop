package com.techshop.userservice.services;

import com.techshop.userservice.common.util.JwtUtil;
import com.techshop.userservice.dto.*;
import com.techshop.userservice.entity.Role;
import com.techshop.userservice.repository.UserRepository;
import com.techshop.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServicesImp implements UserServices {

    private UserRepository repository;
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserServicesImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.encoder = passwordEncoder;
    }
    @Override
    public boolean login(LoginDto user) {
        System.out.println(user.getUsername());
        User dbUser =  repository.getByUsername(user.getUsername());
        if(dbUser != null) {
            if(encoder.matches(user.getPassword(), dbUser.getPassword()) && Objects.equals(dbUser.getActiveFlag(), "Y"))
                return true;
        }
     return false;
    }
    @Override
    public User getUserByName(String username) {
     //   System.out.println(user.getUsername());
        return repository.getByUsername(username);
    }

    @Override
    public User register(RegisterDto user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encode(user.getPassword()));

        if(user.getFlag() != null)
            newUser.setActiveFlag("N");

        return repository.save(newUser);
    }

    @Override
    public User getProfile(String userId) {
        User user = repository.getByUserId(Long.parseLong(userId));
        return user;
    }

    public User getUserByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        if(!user.isPresent())
            throw new IllegalArgumentException("Username does not exist");

        return user.get();
    }
    @Override
    public User updateUser(UpdateUserDto dto) {
        User user = getUserByUsername(dto.getUsername());

        if(dto.getFirstName() != null)
            user.setFirstName(dto.getFirstName());

        if(dto.getLastName() != null)
            user.setLastName(dto.getLastName());

        if(dto.getPhoneNo() != null)
            user.setPhoneNo(dto.getPhoneNo());

        if(dto.getAddress() != null)
            user.setAddress(dto.getAddress() );

        if(dto.getImgUrl() != null)
            user.setImgUrl(dto.getImgUrl());

        if(dto.getEmail() != null)
            user.setEmail(dto.getEmail());

        if(dto.getDateOfBirth() != null)
            user.setDateOfBirth(dto.getDateOfBirth());

        return repository.save(user);
    }

    @Override
    public void changePassword(String username, ChangePasswordDto dto) {
        LoginDto userLogin = new LoginDto();
        userLogin.setPassword(dto.getOldPassword());
        userLogin.setUsername(username);
        try {
          if(login(userLogin)) {
              User user = getUserByUsername(username);
              user.setPassword(encoder.encode((dto.getNewPassword())));
              repository.save(user);
          } else {
              throw new IllegalArgumentException("Old password does not match");
          }
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Old password does not match");
        }
    }

    @Override
    public boolean isTakenEmail(String email) {
        return repository.countByEmail(email) >= 1;
    }

    @Override
    public boolean isTakenUsername(String userName) {
        return repository.countByUsername(userName.toLowerCase()) >= 1;
    }

    @Override
    public List<User> getUsers() {
        return repository.findUsers();
    }

    @Override
    public List<User> getCustomers() {
        return repository.findCustomers();
    }

    @Override
    public void deleteUserByUsername(String username) {
        User userDeleted = getUserByUsername(username);
        userDeleted.setActiveFlag("D");

        repository.save(userDeleted);
    }

    @Override
    public void changeStatus(BlockedUserDto dto) {
        User user = getUserByUsername(dto.getUsername());
        user.setActiveFlag(dto.getFlag());
        repository.save(user);
    }

    @Override
    public User createUser(RegisterDto user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(Role.MANAGER);
        if(user.getFlag() != null)
            newUser.setActiveFlag("N");

        return repository.save(newUser);
    }
}
