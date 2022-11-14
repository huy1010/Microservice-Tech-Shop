package com.techshop.productservice.service;

import com.techshop.productservice.converter.VariantConverter;
import com.techshop.productservice.dto.attribute.UpdateVariantAttributeDto;
import com.techshop.productservice.dto.variant.CreateVariantDto;
import com.techshop.productservice.dto.variant.UpdateVariantDto;
import com.techshop.productservice.dto.variant.VariantWithAttributesDto;
import com.techshop.productservice.entity.*;
import com.techshop.productservice.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VariantServiceImpl  implements VariantService{

    private VariantRepository repository;

    private ProductService productService;
    private AttributeService attributeService;
    private VariantAttributeService variantAttributeService;
    private TagService tagService;

    @Autowired
    private VariantConverter converter;

    public VariantServiceImpl(TagService tagService, VariantRepository repository, ProductService productService, AttributeService attributeService,  VariantAttributeService variantAttributeService){
        this.repository = repository;
        this.productService = productService;
        this.attributeService = attributeService;
        this.variantAttributeService= variantAttributeService;
        this.tagService = tagService;

    }

    @Override
    public List<VariantWithAttributesDto> getByProductId(Long productId) {
        List<Variant> variants = repository.findByProductIdAndActiveFlag(productId, "Y");
//        Variant variant = repository.findById(1L).get();

        List<VariantWithAttributesDto> result = new ArrayList<>();
        variants.forEach(variant -> {
            result.add(converter.toVariantWithAttributes(variant));
        });

        return result;
    }


    @Override
    @Transactional
    public void createVariant(CreateVariantDto dto) {
        Product product = productService.getProductById(dto.getProductId());

        Variant variant = new Variant();
        variant.setProduct(product);
        variant.setVariantName(dto.getVariantName());
        variant.setVariantDesc(dto.getVariantDesc());
        variant.setSku(dto.getSku());
        variant.setImgUrl(dto.getImgUrl());
        variant.setPrice(dto.getPrice());

        for(UpdateVariantAttributeDto createAttribute : dto.getAttributes()){
            VariantAttribute variantAttribute = new VariantAttribute();
            Attribute attribute = attributeService.getById(createAttribute.getAttributeId());

            variantAttribute.setVariant(variant);
            variantAttribute.setAttribute(attribute);
            variantAttribute.setDescription(createAttribute.getDescription());
            variantAttribute.setValue(createAttribute.getValue());

            if (createAttribute.getTagId() != 0){
                Tag tag = tagService.getTagById(createAttribute.getTagId());
                variantAttribute.setTag(tag);
            }

            variant.getAttributes().add(variantAttribute);

        }

        repository.save(variant);

    }

    @Override
    public void updateVariant(UpdateVariantDto dto) {
        Variant variant = getById(dto.getVariantId());
        variant.getAttributes().clear();

        variant.setVariantName(dto.getVariantName());
        variant.setVariantDesc(dto.getVariantDesc());
        variant.setSku(dto.getSku());
        variant.setImgUrl(dto.getImgUrl());
        variant.setPrice(dto.getPrice());

        for(UpdateVariantAttributeDto updateAttribute : dto.getAttributes()){
            VariantAttribute variantAttribute = variantAttributeService.getVariantAttributeById(variant.getVariantId(), updateAttribute.getAttributeId());

            variantAttribute.setDescription(updateAttribute.getDescription());
            variantAttribute.setValue(updateAttribute.getValue());

            if (updateAttribute.getTagId() != 0){
                Tag tag = tagService.getTagById(updateAttribute.getTagId());
                variantAttribute.setTag(tag);
            } else {
                variantAttribute.setTag(null);
            }



            variant.getAttributes().add(variantAttribute);
        }

        repository.save(variant);

    }

    @Override
    public Variant getById(Long variantId) {
        Optional<Variant> variant = repository.findById(variantId);
        if(!variant.isPresent())
            throw new IllegalStateException("Variant with ID: " + variantId + " is not existed");

        return variant.get();
    }

    @Override
    public VariantWithAttributesDto getVariantDetailById(Long variantId) {
        Variant variant = getById(variantId);
        return converter.toVariantWithAttributes(variant);
    }

    @Override
    public void deleteVariant(Long variantId) {
        Variant variant = getById(variantId);
        variant.setActiveFlag("D");
        repository.save(variant);
    }

    @Override
    public void handleQuantity(Long variantId, Integer quantity, String method) {
        Variant variant = getById(variantId);
        if(method.equals("sub")){
            if(variant.getQuantity() < quantity)
                throw new IllegalStateException("Số lượng trong kho không đủ");
            else
                variant.setQuantity(variant.getQuantity() - quantity);
        } else {
            variant.setQuantity(variant.getQuantity() + quantity);
        }

        repository.save(variant);
    }


}
