package com.techshop.productservice.controller;

import com.techshop.productservice.common.ResponseHandler;
import com.techshop.productservice.service.AttributeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/attributes")
public class AttributeController {
    private AttributeService service;

    public AttributeController(AttributeService service){
        this.service= service;
    }

    @GetMapping
    public Object getAll() {
        return ResponseHandler.getResponse(service.getAll(), HttpStatus.OK);
    }
}
