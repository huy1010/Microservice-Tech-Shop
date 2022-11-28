package com.techshop.orderservice.dto.order;

import com.techshop.orderservice.dto.voucher.GetVoucherDto;
import com.techshop.orderservice.entity.OrderStatus;
import com.techshop.orderservice.entity.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetOrderDto {
    private  Long orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private  String username;
    private GetVoucherDto voucher;
    private  Long totalPrice;
    private LocalDateTime createdAt;
    private String deliveryAddress;
    private String phoneNumber;
    private String recipientName;
    private Long discountPrice;
    private List<GetOrderDetailDto> orderDetails;

}
