package com.techshop.orderservice.dto.order;

import com.techshop.orderservice.entity.OrderStatus;
import com.techshop.orderservice.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UpdateOrderDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
    private final PaymentStatus paymentStatus;

}
