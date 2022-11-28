package com.techshop.orderservice.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderWithNoneAccountDto {
    private String deliveryAddress;
    private String phoneNumber;
    private String recipientName;
    private List<CreateOrderDetailDto> orderDetail;
}