package com.techshop.productservice.dto.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeWithTagDto {
    private Long attributeId;
    private String attributeName;
    private String description;
    private String value;
    private String attributeIcon;
    private Long tagId;
    private String tagName;
}
