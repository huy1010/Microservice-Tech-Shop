package com.techshop.productservice.dto.variant;

import com.techshop.productservice.dto.attribute.UpdateVariantAttributeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateVariantDto {
    private Long variantId;
    private String sku;
    private String variantName;
    private String variantDesc;
    private Long price;
    private String imgUrl;
    private List<UpdateVariantAttributeDto> attributes;
}
