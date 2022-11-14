package com.techshop.productservice.converter;

import com.techshop.productservice.service.AttributeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AttributeConverter {
    private AttributeService attributeService;
    private AttributeConverter(@Lazy AttributeService service){
        this.attributeService = service;
    }

//    private static final ProductConverter instance = new ProductConverter();
//    public static ProductConverter getInstance(){
//        return  instance;
//    }

//    public AttributeWithTagDto toAttributeWithTagDto(VariantAttribute variantAttribute){
//        AttributeWithTagDto result = new AttributeWithTagDto();
//
//
//        result.setAttributeId(variantAttribute.getAttribute().getAttributeId());
//        result.setAttributeName( variantAttribute.getAttribute().getAttributeName());
//        result.setDescription(variantAttribute.getDescription());
//        result.setValue( variantAttribute.getValue());
//
//        Tag tag = attributeService.getTagByAttributeId(variantAttribute.getAttribute().getAttributeId());
//        result.setTagId(tag.getTagId());
//        result.setTagName(tag.getTagName());
//
//        return result;
//    }
}