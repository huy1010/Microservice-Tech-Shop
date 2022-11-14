package com.techshop.productservice.dto.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVariantAttributeDto {
    private Long attributeId;
    private Long tagId;
    private String value;
    private String description;
}
