package com.techshop.importservice.dto;

import com.techshop.importservice.entity.ImporterDetail;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class GetImporterDto {
    private  Long importId;
    private  String importDesc;
    private  Long totalPrice;
    private  String emailImporter;
    private  LocalDate createdAt;
    private List<GetImporterDetailDto> importDetails;

}
