package com.techshop.importservice.controller;


import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.importservice.common.ResponseHandler;
import com.techshop.importservice.dto.CreateImporterDto;
import com.techshop.importservice.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/imports")
public class ImporterController {
//    private final ImporterService service;
    private final ProductServiceClient productServiceClient;

    @Autowired
    public ImporterController(ProductServiceClient productServiceClient) {
//        this.service = importerService;
        this.productServiceClient = productServiceClient;
    }

    @GetMapping
    public Object getImports() {
        try {
           Object products = productServiceClient.getBrands(true);
//            List<GetImporterDto> importers = service.getImports();
//            return ResponseHandler.getResponse(importers, HttpStatus.OK);
            return ResponseHandler.getResponse(products, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping(path = "/{import-id}")
//    public Object getImportById(@PathVariable("import-id") Long importId) {
//        try {
//            if (importId == null)
//                throw new IllegalStateException("Import id must not be null");

//            GetImporterDto importer = service.getImport(importId);
//            return ResponseHandler.getResponse(importer, HttpStatus.OK);
//            return "OK";
//        } catch (Exception e) {
//            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
//        }
//    }




    @PostMapping
    public Object createImport(@Valid @RequestBody CreateImporterDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

//            GetImporterDto importer = service.createImport(dto);
//            return ResponseHandler.getResponse(importer, HttpStatus.OK);
            return "OK";
        }
        catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }
}
