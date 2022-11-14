package com.techshop.productservice.service;

import com.techshop.productservice.entity.Attribute;
import com.techshop.productservice.entity.Tag;

import java.util.List;

public interface AttributeService {
//    boolean removeAttribute(Long attributeId);
//    List<Attribute> addAttributesToCategory(Long categoryId, List<AttributeDto> attributes);
        Attribute getById(Long attributeId);
        List<Attribute> getAll();
        Tag getTagByAttributeId(Long attributeId);
}
