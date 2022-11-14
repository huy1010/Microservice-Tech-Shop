package com.techshop.productservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BrandDto {
    private Long brandId;

    @NotEmpty
    @NotBlank
    private String brandName;
    private String brandDesc;
    private String imgUrl;
}
