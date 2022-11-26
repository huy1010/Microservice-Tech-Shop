package com.techshop.productservice.controller;


import com.techshop.productservice.common.ResponseHandler;
import com.techshop.productservice.dto.CategoryDto;
import com.techshop.productservice.entity.Category;
import com.techshop.productservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping()
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public Object getCategories(@RequestParam(value = "onlyActive") Boolean isActive){
        if(isActive)
            return ResponseHandler.getResponse(service.getCategoriesActive(), HttpStatus.OK);

        return ResponseHandler.getResponse(service.getCategories(), HttpStatus.OK);
    }




    @GetMapping(path = "/categories/{category-id}")
    public Object getCategoryById(@PathVariable("category-id") Long categoryId){
        try{
            if(categoryId == null)
                throw new IllegalStateException("Category Id must not be null");

            return service.getCategoryById(categoryId);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/categories")
    public Object createCategory(@Valid @RequestBody CategoryDto newCategory, BindingResult errors) {
        try {
            if(errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR);

            return ResponseHandler.getResponse(service.createCategory(newCategory), HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/categories")
    public Object updateCategory(@RequestBody CategoryDto updatedCategory){
        try{
            Category category = service.updateCategory(updatedCategory);
            if(category == null)
                return ResponseHandler.getResponse(HttpStatus.INTERNAL_SERVER_ERROR);

            return ResponseHandler.getResponse(category, HttpStatus.OK);
        }catch(Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(path = "/categories/{category-id}")
    public Object deleteBrand(@PathVariable("category-id") Long categoryId) {
        try {
            if (categoryId == null)
                throw new IllegalStateException("Category Id must not be null");
            return ResponseHandler.getResponse(service.deleteCategory(categoryId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/categories/remove-attributes")
    public Object removeAttributes(@RequestBody CategoryDto dto) {
        try {
            return ResponseHandler.getResponse( service.removeAttributes(dto),  HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
