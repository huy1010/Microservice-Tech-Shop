package com.techshop.orderservice.servcie;

import com.techshop.orderservice.dto.order.CreateOrderDetailDto;
import com.techshop.orderservice.dto.order.OrderInfo;
import com.techshop.orderservice.dto.order.OrderWithNoneAccountDto;
import com.techshop.orderservice.dto.order.UpdateOrderDto;
import com.techshop.orderservice.entity.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> getOrders();

    Order getOrder(Long orderId);

    Order addCartItem(CreateOrderDetailDto dto);

    Order deleteCartItem(Long variantId);

    Order addVoucher(String voucherName);

    Order removeVoucher();

    List<Order> getYourOrders();

    Order getYourCart();

    Order checkout() throws Exception;

    Order changeOrderStatus(UpdateOrderDto dto);

    Map<LocalDate, List<Order>> getOrderReport(LocalDate start, LocalDate end, String compression);

    Object getBestSeller();

    Boolean deleteOrder(Long orderId);

    void updateInfoCheckOut(OrderInfo orderInfo);

    Order checkoutWithNoneAccount(OrderWithNoneAccountDto dto) throws Exception;

    Object getRevenue();
}
