package com.techshop.productservice.dto.attribute;

import com.techshop.productservice.entity.VariantAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttributeDto {
    private Long attributeId;
    private String attributeName;
    private String description;
    private String value;
    private String attributeIcon;
    private String tagName;
    private Long tagId;

    public AttributeDto(VariantAttribute variantAttribute){
        this.attributeId = variantAttribute.getAttribute().getAttributeId();
        this.attributeIcon = variantAttribute.getAttribute().getAttributeIcon();
        this.attributeName = variantAttribute.getAttribute().getAttributeName();
        this.description = variantAttribute.getDescription();
        this.value = variantAttribute.getValue();

        if(variantAttribute.getTag() !=null){
            this.tagId = variantAttribute.getTag().getTagId();
            this.tagName = variantAttribute.getTag().getTagName();
        }

    }


}
