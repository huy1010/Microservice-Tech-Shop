package com.techshop.userservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user-service/users")
public class UserController {

    @GetMapping()
    public String getAll(){
        return "OKla";
    }

}
