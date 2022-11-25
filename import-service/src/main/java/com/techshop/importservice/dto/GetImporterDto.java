package com.techshop.importservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetImporterDto {
    private  Long importId;
    private  String importDesc;
    private  Long totalPrice;
    private  String emailImporter;
    private  LocalDate createdAt;
    private  List<GetImporterDetailDto> importDetails;

}
