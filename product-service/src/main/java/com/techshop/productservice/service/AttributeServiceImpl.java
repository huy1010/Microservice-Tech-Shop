package com.techshop.productservice.service;

import com.techshop.productservice.entity.Attribute;
import com.techshop.productservice.entity.Tag;
import com.techshop.productservice.repository.AttributeRepository;
import com.techshop.productservice.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeServiceImpl implements AttributeService{
    private  AttributeRepository repository;
    private TagRepository tagRepository;

    public AttributeServiceImpl(AttributeRepository repository, TagRepository tagRepository) {
        this.repository = repository;
        this.tagRepository = tagRepository;

    }

    @Override
    public Attribute getById(Long attributeId) {
        Optional<Attribute> attribute = repository.findById(attributeId);
        if(!attribute.isPresent())
            throw new IllegalArgumentException("Attribute with ID: " + attributeId + " is not existed");

        return attribute.get();
    }

    @Override
    public List<Attribute> getAll() {
        return repository.findAll();
    }

    @Override
    public Tag getTagByAttributeId(Long attributeId) {
        return tagRepository.findTagByAttributeId(attributeId);
    }


//    private Attribute createAttribute(Long categoryId, AttributeDto dto){
//        Attribute attribute = new Attribute();
//        attribute.setAttributeName(dto.getAttributeName());
//        attribute.setCategoryId(categoryId);
//        return repository.save(attribute);
//    }


//    @Override
//    public boolean removeAttribute(Long attributeId) {
//        return false;
//    }
//
//    @Override
//    public List<Attribute> addAttributesToCategory(Long categoryId, List<AttributeDto> attributes) {
//        attributes.forEach(attribute -> createAttribute(categoryId, attribute));
//        return repository.findAttributeByCategoryId(categoryId);
//    }



}
