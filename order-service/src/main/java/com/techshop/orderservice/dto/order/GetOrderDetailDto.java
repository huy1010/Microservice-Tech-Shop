package com.techshop.orderservice.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderDetailDto {
    private  Long variantId;
    private  Integer quantity;
    private Long unitPrice;
    private Object variant;
}
