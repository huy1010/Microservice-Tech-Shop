package com.techshop.importservice.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateImporterDetailDto {
    @NotNull(message = "Variant id must not be null")
    private Long variantId;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must greater than 0")
    private Integer quantity;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must greater than 0")
    private Long price;
}
