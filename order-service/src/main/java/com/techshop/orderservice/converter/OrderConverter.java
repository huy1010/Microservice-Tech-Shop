package com.techshop.orderservice.converter;

import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.clients.productservice.VariantDetailResponse;
import com.techshop.orderservice.dto.order.GetOrderDetailDto;
import com.techshop.orderservice.dto.order.GetOrderDto;
import com.techshop.orderservice.dto.voucher.GetVoucherDto;
import com.techshop.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class OrderConverter {
    @Autowired
    private ProductServiceClient _productServiceClient;


    public GetOrderDto toGetOrderDto(Order order){
        GetOrderDto result = new GetOrderDto();
        result.setOrderId(order.getOrderId());
        result.setOrderStatus(order.getOrderStatus());
        result.setPaymentStatus(order.getPaymentStatus());
        //result.setUsername(order.getUser().getUsername());
        result.setUsername("Hard code");
        result.setVoucher(
                order.getVoucher() == null
                        ? null
                        : new GetVoucherDto(order.getVoucher()));
        result.setTotalPrice(order.getTotalPrice());
        result.setCreatedAt(order.getCreatedAt());
        result.setDeliveryAddress(order.getDeliveryAddress());
        result.setRecipientName(order.getRecipientName());
        order.setPhoneNumber(order.getPhoneNumber());

        List<GetOrderDetailDto> orders = new ArrayList<>();
        order.getOrderDetails().forEach(item -> {
            GetOrderDetailDto detailDto = new GetOrderDetailDto();
            detailDto.setQuantity(item.getQuantity());
            detailDto.setUnitPrice(item.getUnitPrice());
            detailDto.setVariantId(item.getId().getVariantId());

            LinkedHashMap<String, Object> variant = (LinkedHashMap<String, Object>) _productServiceClient.getVariant(item.getId().getVariantId());
            detailDto.setVariant(variant.get("content"));

            orders.add(detailDto);
        });

        if (result.getVoucher() != null){
            Long discountPrice = order.getTotalPrice() * order.getVoucher().getVoucherValue() / 100;
            if(discountPrice > order.getVoucher().getCappedAt())
                result.setDiscountPrice(order.getVoucher().getCappedAt());
            else
                result.setDiscountPrice(discountPrice);
        } else {
            result.setDiscountPrice(0L);
        }


        result.setOrderDetails(orders);

        return result;
    }
}