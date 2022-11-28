package com.techshop.productservice.service;

import com.techshop.clients.productservice.UpdateVariantRequest;
import com.techshop.productservice.dto.variant.CreateVariantDto;
import com.techshop.productservice.dto.variant.UpdateVariantDto;
import com.techshop.productservice.dto.variant.VariantWithAttributesDto;
import com.techshop.productservice.entity.Variant;

import java.util.List;

public interface VariantService {
    List<VariantWithAttributesDto> getByProductId(Long productId);
    void createVariant(CreateVariantDto dto);
    void updateVariant(UpdateVariantDto dto);
    Variant getById(Long variantId);

    VariantWithAttributesDto getVariantDetailById(Long variantId);

    void deleteVariant(Long variantId);

    void handleQuantity(Long variantId, Integer quantity, String method);

    String enoughQuantity(List<UpdateVariantRequest> requests);

    Boolean existsVariant(Long variantId);

    void updateInventory(List<UpdateVariantRequest> inventories);

    void updateForOrder(List<UpdateVariantRequest> variants);
}
