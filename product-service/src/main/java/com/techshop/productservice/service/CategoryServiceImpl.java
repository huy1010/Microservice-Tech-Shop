package com.techshop.productservice.service;


import com.techshop.productservice.common.util.StringUtils;
import com.techshop.productservice.dto.CategoryDto;
import com.techshop.productservice.entity.Attribute;
import com.techshop.productservice.entity.Category;
import com.techshop.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository repo;
    private final AttributeService attributeService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, AttributeService attributeService) {
        this.repo = repository;
        this.attributeService = attributeService;
    }

    @Override
    public List<Category> getCategories() {
        return repo.findAll();
    }

    @Override
    public List<Category> getCategoriesActive() {
        return repo.findByActiveFlag("Y");
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = repo.findById(categoryId);
        if(category.isPresent())
            return category.get();

        throw new IllegalStateException("Category does not exist");
    }

    @Override
    public Boolean deleteCategory(Long categoryId) {
        if(!isExisted(categoryId))
            throw new IllegalStateException("Category does not exist");

        Category category = repo.getById(categoryId);
        category.setActiveFlag("D");
        repo.save(category);
        return true;
    }

    @Override
    public Category createCategory(CategoryDto dto) {
        Category category = handleData(dto, false);

//        List<Attribute> attributes = attributeService.addAttributesToCategory(category.getCategoryId(), dto.getAttributes());
        dto.getAttributes().forEach(attribute -> {
            Attribute addedAttribute = attributeService.getById(attribute.getAttributeId());
            category.addAttribute(addedAttribute);
        });

        return repo.save(category);
    }

    @Override
    public Category updateCategory(CategoryDto dto) {
        Category category = handleData(dto, true);
        return repo.save(category);
    }

    public boolean isExisted(Long categoryId){
        Optional<Category> category = repo.findById(categoryId);
        if(category.isPresent())
            return true;

        return false;
    }

    @Override
    public boolean removeAttributes(CategoryDto dto) {
        Category category = getCategoryById(dto.getCategoryId());

        dto.getAttributes().forEach(attribute -> {
            Attribute removedAttribute = attributeService.getById(attribute.getAttributeId());
            category.removeAttribute(removedAttribute);
        });
        repo.save(category);
        return true;
    }

    @Override
    public Category getCategoryByLink(String categoryLink) {
        return repo.findByCategoryLink(categoryLink);
    }

    public Category handleData(CategoryDto dto, boolean hasId){
        Category category = new Category();

        if(hasId) {
            if (dto.getCategoryId() == null)
                    throw new IllegalStateException("Category Id must not be null");

            if (isExisted(dto.getCategoryId()))
                category = repo.getById(dto.getCategoryId());
        }

        if(dto.getCategoryName() != null)
            category.setCategoryName(dto.getCategoryName());
            String categoryLink = StringUtils.deAccent(dto.getCategoryName());
            category.setCategoryLink(categoryLink);

        if(dto.getCategoryDesc() !=null) {
            category.setCategoryDesc(dto.getCategoryDesc());
        }

        if(dto.getImgUrl() !=null) {
            category.setImgUrl(dto.getImgUrl());
        }


        if(dto.getParentId() != null) {
            if(!isExisted(dto.getParentId()))
                throw new IllegalStateException("Parent does not exist");

            Category parent = repo.getById(dto.getParentId());
            category.setParent(parent);
        }
        return  category;
    }
}