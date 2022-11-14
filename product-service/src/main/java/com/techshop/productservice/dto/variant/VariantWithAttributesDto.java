package com.techshop.productservice.dto.variant;

import com.techshop.productservice.dto.attribute.AttributeDto;
import lombok.Data;

import java.util.List;

@Data
public class VariantWithAttributesDto {

    private Long variantId;
    private String sku;
    private String variantName;
    private String variantDesc;
    private Long price;
    private String imgUrl;
    private Integer quantity;
    private List<AttributeDto> attributes;
    private String productName;
    private String productLink;
    private Long importPrice;

//    public VariantWithAttributesDto(Variant variant){
//
//        this.variantId = variant.getVariantId();
//        this.sku = variant.getSku();
//        this.variantName = variant.getVariantName();
//        this.variantDesc = variant.getVariantDesc();
//        this.imgUrl = variant.getImgUrl();
//        this.price = variant.getPrice();
//        this.attributes = variant.getAttributes().isEmpty() ? new ArrayList<>() :  variant.getAttributes().stream().map(AttributeDto::new).collect(Collectors.toList());
//    }
}
