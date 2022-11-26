package com.techshop.userservice.controller;

import com.techshop.userservice.common.ResponseHandler;
import com.techshop.userservice.common.util.JwtUtil;
import com.techshop.userservice.dto.ChangePasswordDto;
import com.techshop.userservice.dto.UpdateUserDto;
import com.techshop.userservice.entity.User;
import com.techshop.userservice.services.UserServices;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping(path = "/profile/me")
    public Object getProfile(@RequestHeader("Authorization") String token){
        try{
            Claims claims = jwtUtil.getClaims(token);
            return ResponseHandler.getResponse(userServices.getProfile(claims.get("jti").toString()), HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/profile/me")
    public Object updateProfile(@RequestBody UpdateUserDto dto, BindingResult errors) {
        try{
            if(errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

            dto.setRoleId(null);
            User updatedUser = userServices.updateUser(dto);

            return ResponseHandler.getResponse(updatedUser, HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/me/change-password")
    public Object changePassword(@RequestHeader("Authorization") String token, @Valid @RequestBody ChangePasswordDto dto, BindingResult errors) {
        try{
            Claims claims = jwtUtil.getClaims(token);
            if(errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

            userServices.changePassword(claims.get("jti").toString(), dto);

            return ResponseHandler.getResponse(HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
