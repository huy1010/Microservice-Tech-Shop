package com.techshop.productservice.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductDto {
    private Long productId;
    private Long brandId;

    @NotNull
    private Long categoryId;
    private String productName;
    private String productDesc;
    private String imgUrl;
}

