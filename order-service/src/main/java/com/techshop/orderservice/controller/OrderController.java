package com.techshop.orderservice.controller;



import com.techshop.shared.common.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping()
    public Object check(){
        return ResponseHandler.getResponse("OKLA", HttpStatus.OK);
    }
}
