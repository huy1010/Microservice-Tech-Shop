package com.techshop.orderservice.dto.order;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateOrderDetailDto {
    @NotNull(message = "{variant.not-null}")
    private Long variantId;

    @NotNull(message = "{quantity.not-null}")
    @Positive(message = "{quantity.positive}")
    private Integer quantity;
}