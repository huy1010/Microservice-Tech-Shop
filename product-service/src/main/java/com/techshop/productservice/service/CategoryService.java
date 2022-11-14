package com.techshop.productservice.service;

import com.techshop.productservice.dto.CategoryDto;
import com.techshop.productservice.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    List<Category> getCategoriesActive();
    Category getCategoryById(Long categoryId);
    Boolean deleteCategory(Long categoryId);
    Category createCategory(CategoryDto dto);
    Category updateCategory(CategoryDto updatedCategory);
    boolean isExisted(Long categoryId);

    boolean removeAttributes(CategoryDto dto);

    Category getCategoryByLink(String categoryLink);
}
