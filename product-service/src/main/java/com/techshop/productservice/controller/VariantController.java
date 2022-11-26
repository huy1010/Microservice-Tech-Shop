package com.techshop.productservice.controller;

import com.techshop.clients.productservice.UpdateVariantRequest;
import com.techshop.productservice.common.ResponseHandler;
import com.techshop.productservice.dto.variant.CreateVariantDto;
import com.techshop.productservice.dto.variant.UpdateVariantDto;
import com.techshop.productservice.entity.Variant;
import com.techshop.productservice.service.VariantService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("variants")
public class VariantController {
    private VariantService service;

    public VariantController(VariantService service){
        this.service = service;
    }

    @GetMapping("/{variant-id}")
    public Object getVariantDetailById(@PathVariable("variant-id") Long variantId) {
        try {

            return ResponseHandler.getResponse(service.getVariantDetailById(variantId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public Object createVariant(@Valid @RequestBody CreateVariantDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

            service.createVariant(dto);
            return ResponseHandler.getResponse( HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping()
    public Object updateVariant(@Valid @RequestBody UpdateVariantDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);
//
            service.updateVariant(dto);
            return ResponseHandler.getResponse( HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{variant-id}")
    public Object deleteVariant(@PathVariable("variant-id") Long variantId){
        try{
            service.deleteVariant(variantId);
            return ResponseHandler.getResponse(HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{variant-id}/exists")
    public Boolean existsVariant(@PathVariable("variant-id") Long variantId){
        try{
            return service.existsVariant(variantId);
        }catch (Exception e){
           return false;
        }
    }
    @PutMapping("inventories")
    void updateVariantInventory(@RequestBody List<UpdateVariantRequest> requests){
       service.updateInventory(requests);
    }
}
