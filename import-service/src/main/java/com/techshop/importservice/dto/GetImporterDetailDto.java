package com.techshop.importservice.dto;


//import com.techshop.product.dto.variant.VariantWithAttributesDto;
import com.techshop.importservice.entity.ImporterDetail;
import lombok.Data;

@Data
public class GetImporterDetailDto {
    private  Long variantId;
    private  Integer quantity;
    private  Long price;
//
    public GetImporterDetailDto(ImporterDetail importerDetail) {
        this.variantId = importerDetail.getId().getVariantId();
        this.quantity = importerDetail.getQuantity();
        this.price = importerDetail.getPrice();
    }
}
