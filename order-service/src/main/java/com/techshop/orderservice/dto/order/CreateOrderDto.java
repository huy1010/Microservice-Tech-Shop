package com.techshop.orderservice.dto.order;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CreateOrderDto {
    @NotNull
    private Long voucherId;

    @NotNull(message = "{list-order-detail.not-null}")
    @NotEmpty(message = "{list-order-detail.not-empty}")
    private List<@Valid CreateOrderDetailDto> orderDetails;
}
