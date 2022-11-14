package com.techshop.productservice.dto.product;

import com.techshop.productservice.entity.Brand;
import com.techshop.productservice.entity.Category;
import com.techshop.productservice.entity.Variant;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class ProductDetailDto {
    private Long productId;
    private String productName;
    private String productDesc;
    private String imgUrl;
    private String activeFlag = "Y";
    private Brand brand;
    private Category category;
    private Collection<Variant> variants = new ArrayList<>();

}
