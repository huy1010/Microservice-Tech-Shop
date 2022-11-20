package com.techshop.importservice.service;


import com.techshop.importservice.common.util.AdjusterUtils;
import com.techshop.importservice.converter.ImportConverter;
import com.techshop.importservice.dto.CreateImporterDetailDto;
import com.techshop.importservice.dto.CreateImporterDto;
import com.techshop.importservice.dto.GetImporterDetailDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.entity.Importer;
import com.techshop.importservice.entity.ImporterDetail;
import com.techshop.importservice.repository.ImporterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImporterServiceImpl implements ImporterService {
//    private  ImporterRepository repository;
//    private  VariantService variantService;
//    private  UserService userService;
//    private ImportConverter importConverter;
//
//    @Autowired
//    public ImporterServiceImpl(ImporterRepository repository, @Lazy VariantService variantService, UserService userService, @Lazy ImportConverter importConverter) {
//        this.repository = repository;
//        this.variantService = variantService;
//        this.userService = userService;
//        this.importConverter = importConverter;
//    }
//
//    @Override
//    public List<GetImporterDto> getImports() {
//        List<Importer> importers = repository.findAll();
//        List<GetImporterDto> result = new ArrayList<>();
//        importers.forEach(item -> result.add(importConverter.toGetImportDto(item)));
//
//        return result;
//    }
//
//    @Override
//    public GetImporterDto getImport(Long importId) {
//        Importer result = repository.findById(importId).orElse(null);
//
//        return importConverter.toGetImportDto(result);
//    }
//
//    @Override
//    public GetImporterDto createImport(CreateImporterDto dto) {
//        Importer importer = new Importer();
//
//        if (dto.getImportDesc() != null) {
//            importer.setImportDesc(dto.getImportDesc());
//        }
//
//        importer.setUser(userService.getProfile());
//
//        for (CreateImporterDetailDto createImporterDetail : dto.getImportDetails()) {
//            ImporterDetail importerDetail = new ImporterDetail();
//
//            Variant variant = variantService.getById(createImporterDetail.getVariantId());
//
//            Integer oldQuantity = variant.getQuantity();
//
//            importerDetail.setQuantity(createImporterDetail.getQuantity());
//            importerDetail.setPrice(createImporterDetail.getPrice());
//            importerDetail.setImporter(importer);
//            importerDetail.setVariant(variant);
//
//            importerDetail.getVariant().setQuantity(oldQuantity + importerDetail.getQuantity());
//            importerDetail.getVariant().setImportPrice(importerDetail.getPrice());
//
//            importer.getImportDetails().add(importerDetail);
//        }
//
//        Importer result = repository.save(importer);
//
//        return importConverter.toGetImportDto(result);
//    }
//
//    @Override
//    public Map<LocalDate, List<Importer>> getImportReport(LocalDate start, LocalDate end, String compression) {
//
//        return repository.findByCreatedAtBetweenOrderByCreatedAt(start.atStartOfDay(), end.atTime(LocalTime.MAX))
//                .stream().collect(Collectors.groupingBy(item ->
//                        item.getCreatedAt().toLocalDate().with(AdjusterUtils.getAdjuster().get(compression))));
//    }
//
//    @Override
//    public List<GetImporterDetailDto> getImportDetail(Set<ImporterDetail> importDetails) {
//        List<GetImporterDetailDto> result = new ArrayList<>();
//        importDetails.forEach(detail -> {
//            GetImporterDetailDto item = new GetImporterDetailDto();
//            item.setQuantity(detail.getQuantity());
//            item.setPrice(detail.getPrice());
//
//            VariantWithAttributesDto variant = variantService.getVariantDetailById(detail.getVariant().getVariantId());
//            item.setVariant(variant);
//
//            result.add(item);
//        });
//
//        return result;
//    }
//
//    @Override
//    public Object getTotalCost() {
//        List<Importer> importers = repository.findAll();
//        return new HashMap<String, Object>() {{
//            put("count_import", importers.size());
//            put("total_cost", importers.stream().map(Importer::getTotalPrice).mapToLong(Long::longValue).sum());
//        }};
//    }
}
