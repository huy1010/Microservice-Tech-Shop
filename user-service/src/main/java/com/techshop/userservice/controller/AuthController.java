package com.techshop.userservice.controller;

import com.techshop.userservice.common.ResponseHandler;
import com.techshop.userservice.dto.LoginDto;
import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.entity.User;
import com.techshop.userservice.services.UserServices;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.techshop.userservice.common.util.JwtUtil;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    private final UserServices userServices;


    public AuthController(UserServices userService) {
        this.userServices = userService;
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);
            if (userServices.login(dto)) {
                String token = jwtUtil.generateToken(dto.getUsername());
                return ResponseHandler.getResponse(token, HttpStatus.OK);
            } else {
                return ResponseHandler.getResponse(errors, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/register")
    public Object  register(@Valid @RequestBody RegisterDto dto, BindingResult errors) {
        try{
            if(errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

            dto.setRoleId(null);
            dto.setFlag("N");
            User addedUser = userServices.register(dto);
           // VerificationToken token =securityUserService.createVerificationToken(addedUser);
           // mailService.sendVerifyMail(token.getUser().getEmail(), token.getToken());

            return ResponseHandler.getResponse(HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
