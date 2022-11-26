package com.techshop.userservice.services;

import com.techshop.userservice.dto.ChangePasswordDto;
import com.techshop.userservice.dto.UpdateUserDto;
import com.techshop.userservice.repository.UserRepository;
import com.techshop.userservice.dto.LoginDto;
import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImp implements UserServices {

    private UserRepository repository;
    private PasswordEncoder encoder;

    @Autowired
    public UserServicesImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.encoder = passwordEncoder;
    }
    @Override
    public boolean login(LoginDto user) {
      //  String passEncode = encoder.encode(user.getPassword());
        System.out.println(user.getUsername());
        User dbUser =  repository.getByUsername(user.getUsername());
        if(dbUser != null) {
            if(encoder.matches(user.getPassword(), dbUser.getPassword()))
            return true;
        }
     return false;
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
    public User getProfile(String username) {
        User user = repository.getByUsername(username);
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
              return;
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
}
