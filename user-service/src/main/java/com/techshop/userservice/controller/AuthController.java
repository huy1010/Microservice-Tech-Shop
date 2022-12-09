package com.techshop.userservice.controller;

import com.techshop.userservice.common.ResponseHandler;
import com.techshop.userservice.dto.ForgotPasswordDto;
import com.techshop.userservice.dto.LoginDto;
import com.techshop.userservice.dto.RegisterDto;
import com.techshop.userservice.entity.PasswordResetToken;
import com.techshop.userservice.services.SecurityUserService;
import com.techshop.userservice.services.UserServices;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.techshop.userservice.common.util.JwtUtil;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    private final UserServices userServices;
    private final SecurityUserService securityUserService;



    public AuthController(UserServices userService, SecurityUserService securityUserService) {
        this.userServices = userService;
        this.securityUserService = securityUserService;
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);
            boolean result = userServices.login(dto);
            if (result) {
                User dbUser = userServices.getUserByName(dto.getUsername());
                String token = jwtUtil.generateToken(dbUser.getUserId().toString());

                HashMap<String, Object> response = new HashMap<>();
                response.put("accessToken", token);
                response.put("role", dbUser.getRole());

                return ResponseHandler.getResponse(response, HttpStatus.OK);

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

            dto.setFlag("N");
            userServices.register(dto);

            return ResponseHandler.getResponse(HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm-email")
    public Object confirmEmail(@Param("token") String token) {
        try{
            securityUserService.verifyMailToken(token);
            return ResponseHandler.getResponse(HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path= "/forgot-password")
    public Object forgotPassword(@Param("email") String email) {
        try{
//
//            String token = securityUserService.getForgotPasswordToken(email);
            PasswordResetToken token =securityUserService.createPasswordResetToken(email);
            userServices.sendVerifyResetPassword(token.getUser().getEmail(), token.getToken());
            return ResponseHandler.getResponse(HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/reset-password")
    public Object confirmForgotPassword(@RequestBody ForgotPasswordDto dto) {
        try{
            securityUserService.verifyPasswordResetToken(dto);
            return ResponseHandler.getResponse(HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
