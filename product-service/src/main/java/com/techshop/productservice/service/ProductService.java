package com.techshop.productservice.service;

import com.techshop.productservice.dto.product.ProductDto;
import com.techshop.productservice.dto.product.ProductWithVariantDto;
import com.techshop.productservice.entity.Product;
import com.techshop.productservice.search.ProductSearchCriteria;

import java.util.List;

public interface ProductService {
    List<ProductWithVariantDto> getProducts();
    Object getAll(ProductSearchCriteria searchCriteria);
    Product getProductById(Long id);
    ProductWithVariantDto createProduct(ProductDto dto);
    ProductWithVariantDto updateProduct(ProductDto dto);
    Boolean deleteProduct(Long productId);
//    ProductDetailDto getVariantsByProductId(Long productId);
    ProductWithVariantDto getProductDetailById(Long productId);
    ProductWithVariantDto getByProductLink(String productLink);

    List<ProductWithVariantDto> getProductByCategoryLink(String categoryLink);

    List<ProductWithVariantDto>  getProductByBrand(String brandName);
}
