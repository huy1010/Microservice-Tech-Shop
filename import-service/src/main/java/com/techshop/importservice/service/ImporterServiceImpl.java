package com.techshop.importservice.service;


import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.clients.productservice.UpdateVariantRequest;
import com.techshop.importservice.converter.ImportConverter;
import com.techshop.importservice.dto.CreateImporterDetailDto;
import com.techshop.importservice.dto.CreateImporterDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.entity.Importer;
import com.techshop.importservice.entity.ImporterDetail;
import com.techshop.importservice.entity.ImporterDetailPK;
import com.techshop.importservice.repository.ImporterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImporterServiceImpl implements ImporterService {
    private  ImporterRepository repository;
    private final ProductServiceClient _productServiceClient;
//    private  VariantService variantService;
//    private  UserService userService;
    private ImportConverter importConverter;
//
    @Autowired
    public ImporterServiceImpl(
            ImporterRepository repository,
            @Lazy ImportConverter importConverter,
            ProductServiceClient productServiceClient) {
        this.repository = repository;
        _productServiceClient = productServiceClient;
//        this.variantService = variantService;
//        this.userService = userService;
        this.importConverter = importConverter;
    }
//
//    @OverrideF
    public List<GetImporterDto> getImports() {
        List<Importer> importers = repository.findAll();
        List<GetImporterDto> result = new ArrayList<>();
        importers.forEach(item -> result.add(importConverter.toGetImportDto(item)));

        return result;
    }
//
    @Override
    public GetImporterDto getImport(Long importId) {
        Importer result = repository.findById(importId).orElse(null);

        return importConverter.toGetImportDto(result);
    }
//
    @Transactional
    @Override
    public void createImport(CreateImporterDto dto) {
        Importer importer = new Importer();

        if (dto.getImportDesc() != null) {
            importer.setImportDesc(dto.getImportDesc());
        }

//        importer.setUser(userService.getProfile());
        List<UpdateVariantRequest> inventories = new ArrayList<>();
        for (CreateImporterDetailDto createImporterDetail : dto.getImportDetails()) {
            Long variantId = createImporterDetail.getVariantId();
            Boolean isExist = _productServiceClient.existsVariant(variantId);
            if(!isExist)
                throw new IllegalStateException(variantId + " is not exist");

            ImporterDetail importerDetail = new ImporterDetail();
            importerDetail.getId().setVariantId(variantId);



//            Integer oldQuantity = variant.getQuantity();

            importerDetail.setQuantity(createImporterDetail.getQuantity());
            importerDetail.setPrice(createImporterDetail.getPrice());
            importerDetail.setImporter(importer);
//            importerDetail.setVariantId(createImporterDetail.getVariantId());

            //UPDATE PRICE / QUANTITY
//            importerDetail.getVariant().setQuantity(oldQuantity + importerDetail.getQuantity());
//            importerDetail.getVariant().setImportPrice(importerDetail.getPrice());
            UpdateVariantRequest inventory = new UpdateVariantRequest();
            inventory.setImportPrice(createImporterDetail.getPrice());
            inventory.setVariantId(variantId);
            inventory.setQuantity(createImporterDetail.getQuantity());
            inventories.add(inventory);


            importer.getImportDetails().add(importerDetail);
        }
        repository.save(importer);
        _productServiceClient.updateVariantInventory(inventories);
//        Importer result = repository.save(importer);

//        return importConverter.toGetImportDto(result);
    }

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
