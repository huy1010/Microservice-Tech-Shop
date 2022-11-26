package com.techshop.clients.productservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVariantRequest {
    private Long variantId;
    private Integer quantity;
    private Long importPrice;
}
