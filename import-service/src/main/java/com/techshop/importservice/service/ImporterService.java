package com.techshop.importservice.service;

import com.techshop.importservice.dto.CreateImporterDto;
import com.techshop.importservice.dto.GetImporterDetailDto;
import com.techshop.importservice.dto.GetImporterDto;
import com.techshop.importservice.entity.Importer;
import com.techshop.importservice.entity.ImporterDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ImporterService {
    List<GetImporterDto> getImports();
    GetImporterDto getImport(Long importId);
    void  createImport(CreateImporterDto dto);
//    Map<LocalDate, List<Importer>> getImportReport(LocalDate start, LocalDate end, String compression);
//
//    List<GetImporterDetailDto> getImportDetail(Set<ImporterDetail> importDetails);
//
//    Object getTotalCost();
}
