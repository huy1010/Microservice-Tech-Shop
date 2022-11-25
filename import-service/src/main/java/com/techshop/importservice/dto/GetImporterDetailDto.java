package com.techshop.importservice.dto;


//import com.techshop.product.dto.variant.VariantWithAttributesDto;
import lombok.Data;

@Data
public class GetImporterDetailDto {
//    private  VariantWithAttributesDto variant;
    private  Integer quantity;
    private  Long price;
//
//    public GetImporterDetailDto(ImporterDetail importerDetail) {
//        this.variant = new GetVariantDto(importerDetail.getVariant());
//        this.quantity = importerDetail.getQuantity();
//        this.price = importerDetail.getPrice();
//    }
}
