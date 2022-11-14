package com.techshop.productservice.controller;

import com.techshop.productservice.common.ResponseHandler;
import com.techshop.productservice.dto.BrandDto;
import com.techshop.productservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService service;

    @Autowired
    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping
    public Object getBrands(@RequestParam(value = "onlyActive") Boolean isActive){
        if(isActive)
            return ResponseHandler.getResponse(service.getBrandsActive(), HttpStatus.OK);

        return ResponseHandler.getResponse(service.getBrands(), HttpStatus.OK);
    }


    @GetMapping(path = "/{brand-id}")
    public Object getBrandById(@PathVariable("brand-id") Long brandId){
        try{
            if(brandId == null)
                throw new IllegalStateException("Brand Id must not be null");

            return ResponseHandler.getResponse(service.getBrandById(brandId), HttpStatus.OK);
        } catch (Exception e){
             return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public Object createBrand(@Valid @RequestBody BrandDto newBrand, BindingResult errors){
        try{
            if(errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);

            return ResponseHandler.getResponse(service.createBrand(newBrand), HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    public Object updateBrand(@RequestBody BrandDto updatedBrand, BindingResult errors){
        try{
            if(errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);

            return ResponseHandler.getResponse(service.updateBrand(updatedBrand), HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{brand-id}")
    public Object deleteBrand(@PathVariable("brand-id") Long brandId){
        try{
            if(brandId == null)
                throw new IllegalStateException("Brand Id must not be null");

            return ResponseHandler.getResponse(service.deleteBrand(brandId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

