package com.techshop.importservice.converter;

import com.techshop.importservice.dto.GetImporterDetailDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.entity.Importer;
import com.techshop.importservice.service.ImporterService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportConverter {
    private ImporterService importerService;

    public ImportConverter(@Lazy ImporterService importerService){
        this.importerService = importerService;
    }

    public GetImporterDto toGetImportDto (Importer importer){
            GetImporterDto result = new GetImporterDto();

//        result.setImportId(importer.getImportId());
//        result.setImportDesc(importer.getImportDesc());
//        result.setTotalPrice(importer.getTotalPrice());
//        result.setEmailImporter(importer.getUser().getEmail());
//        result.setCreatedAt(importer.getCreatedAt().toLocalDate());
//
//        List<GetImporterDetailDto> importerDetailDtos = importerService.getImportDetail(importer.getImportDetails());
//        result.setImportDetails(importerDetailDtos);

        return result;
    }
}
