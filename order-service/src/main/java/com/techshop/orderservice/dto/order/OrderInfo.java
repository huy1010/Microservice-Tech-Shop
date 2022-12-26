package com.techshop.orderservice.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfo {
    private String deliveryAddress;
    private String phoneNumber;
    private String recipientName;
    private String email;

}
