package com.techshop.userservice.services;

import com.techshop.userservice.repository.UserRepository;
import com.techshop.userservice.dto.LoginDto;
import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public boolean isTakenEmail(String email) {
        return repository.countByEmail(email) >= 1;
    }

    @Override
    public boolean isTakenUsername(String userName) {
        return repository.countByUsername(userName.toLowerCase()) >= 1;
    }
}
