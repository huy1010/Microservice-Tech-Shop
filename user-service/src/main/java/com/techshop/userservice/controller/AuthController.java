package com.techshop.userservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.techshop.userservice.util.JwtUtil;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String userName) {
        String token = jwtUtil.generateToken(userName);

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String userName) {
        // Persist user to some persistent storage
        System.out.println("Info saved...");

        return new ResponseEntity<String>("Registered", HttpStatus.OK);
    }
}
