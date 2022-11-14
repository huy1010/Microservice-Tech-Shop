package com.techshop.productservice.service;

import com.techshop.productservice.entity.VariantAttribute;

public interface VariantAttributeService {
    VariantAttribute getVariantAttributeById(Long variantId, Long attributeId);
}
