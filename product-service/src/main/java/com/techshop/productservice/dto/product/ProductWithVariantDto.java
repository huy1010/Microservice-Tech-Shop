package com.techshop.productservice.dto.product;


import com.techshop.productservice.dto.variant.VariantWithAttributesDto;
import com.techshop.productservice.entity.Brand;
import com.techshop.productservice.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class ProductWithVariantDto {
    private Long productId;
    private Brand brand;
    private Category category;
    private String productName;
    private String productDesc;
    private String imgUrl;
    private String productLink;
    private Integer totalVariant;
    private List<VariantWithAttributesDto> variants;

//    public ProductWithVariantDto(Product product) {
//        this.productId = product.getProductId();
//        this.productName = product.getProductName();
//        this.category = product.getCategory();
//        this.brand = product.getBrand();
//        this.productDesc = product.getProductDesc();
//        this.imgUrl = product.getImgUrl();
//        this.variants = product.getVariants().isEmpty()
//                ? new ArrayList<>()
//                : product.getVariants().stream().map(VariantWithAttributesDto::new)
//                .collect(Collectors.toList());
//    }
}
