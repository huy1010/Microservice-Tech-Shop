package com.techshop.importservice.controller;


import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.importservice.common.ResponseHandler;
import com.techshop.importservice.dto.CreateImporterDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/imports")
public class ImporterController {
    private final ImporterService service;
    private final ProductServiceClient _productServiceClient;

    @Autowired
    public ImporterController(ImporterService importerService, ProductServiceClient productServiceClient) {
        this.service = importerService;
        this._productServiceClient = productServiceClient;
    }

    @GetMapping
    public Object getImports() {
        try {
            List<GetImporterDto> importers = service.getImports();
            return ResponseHandler.getResponse(importers, HttpStatus.OK);
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

            service.createImport(dto);
            return ResponseHandler.getResponse( HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }
}
