package com.techshop.productservice.converter;

import com.techshop.productservice.dto.attribute.AttributeDto;
import com.techshop.productservice.dto.variant.VariantWithAttributesDto;
import com.techshop.productservice.entity.Variant;
import com.techshop.productservice.service.AttributeService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VariantConverter {
    private AttributeService attributeService;

    private VariantConverter(AttributeService attributeService){
        this.attributeService = attributeService;
    }

    public VariantWithAttributesDto toVariantWithAttributes(Variant variant) {
        VariantWithAttributesDto result = new VariantWithAttributesDto();

        result.setVariantId(variant.getVariantId());
        result.setSku(variant.getSku());
        result.setVariantName(variant.getVariantName());
        result.setVariantDesc(variant.getVariantDesc());
        result.setImgUrl(variant.getImgUrl());
        result.setPrice(variant.getPrice());
        result.setQuantity(variant.getQuantity());
        result.setProductName(variant.getProduct().getProductName());
        result.setProductLink(variant.getProduct().getProductLink());
        result.setImportPrice(variant.getImportPrice());

        List<AttributeDto> attributes = variant.getAttributes().isEmpty()
                                        ? new ArrayList<>()
                                        : variant.getAttributes().stream().sorted((o1, o2) -> Long.compare(o1.getAttribute().getAttributeId(), o2.getAttribute().getAttributeId())).map(AttributeDto::new).collect(Collectors.toList());

        result.setAttributes(attributes);
        return result;
    }
}
