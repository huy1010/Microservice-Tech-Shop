package com.techshop.importservice.controller;


import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.importservice.common.ResponseHandler;
import com.techshop.importservice.converter.ImportConverter;
import com.techshop.importservice.dto.CreateImporterDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/imports")
public class ImporterController {
    private final ImporterService service;
    private ImportConverter importConverter;

    @Autowired
    public ImporterController(ImporterService importerService,  ImportConverter importConverter) {
        this.service = importerService;
        this.importConverter = importConverter;
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

    @GetMapping(path = "/{import-id}")
    public Object getImportById(@PathVariable("import-id") Long importId) {
        try {
            if (importId == null)
                throw new IllegalStateException("Import id must not be null");

            GetImporterDto importer = service.getImport(importId);
            return ResponseHandler.getResponse(importer, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

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

    @GetMapping("/report")
    public Object getImportReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                  @RequestParam String compression){
        try {
            Map<LocalDate, List<GetImporterDto>> result = service.getImportReport(start, end, compression)
                    .entrySet().stream().collect(
                            Collectors.toMap(Map.Entry::getKey,
                                    e -> e.getValue().stream().map(item -> importConverter.toGetImportDto(item))
                                            .collect(Collectors.toList())));

            return ResponseHandler.getResponse(result, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/total-cost")
    public Object getTotalCost() {
        try {
            return ResponseHandler.getResponse(service.getTotalCost(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }
}
