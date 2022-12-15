package com.techshop.importservice.converter;

import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.clients.userservice.UserServiceClient;
import com.techshop.importservice.config.FeignClientInterceptor;
import com.techshop.importservice.dto.GetImporterDetailDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.entity.Importer;
import com.techshop.importservice.service.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImportConverter {
    private ImporterService importerService;
    @Autowired
    private ProductServiceClient _productServiceClient;
    @Autowired
    private UserServiceClient _userServicesClient;
    public ImportConverter(@Lazy ImporterService importerService){
        this.importerService = importerService;
    }

    public GetImporterDto toGetImportDto (Importer importer){
            GetImporterDto result = new GetImporterDto();

        result.setImportId(importer.getImportId());
        result.setImportDesc(importer.getImportDesc());
        result.setTotalPrice(importer.getTotalPrice());

        result.setCreatedAt(importer.getCreatedAt().toLocalDate());
        LinkedHashMap<String, Object> user = (LinkedHashMap<String, Object>) _userServicesClient.getProfile(FeignClientInterceptor.getTokenHeader());
        result.setUser(user.get("content"));
        List<GetImporterDetailDto> importerDetailDtos =
                importer.getImportDetails().stream().map(detail -> {
                         GetImporterDetailDto item =  new GetImporterDetailDto(detail);
                         LinkedHashMap<String, Object> variant = (LinkedHashMap<String, Object>) _productServiceClient.getVariant(detail.getId().getVariantId());
                         item.setVariant(variant.get("content"));
                         return item;
                        }
                ).collect(Collectors.toList());
        result.setImportDetails(importerDetailDtos);

        return result;
    }
}
