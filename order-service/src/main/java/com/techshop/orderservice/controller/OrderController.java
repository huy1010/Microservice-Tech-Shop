package com.techshop.orderservice.controller;



import com.techshop.orderservice.converter.OrderConverter;
import com.techshop.orderservice.dto.order.*;
import com.techshop.orderservice.entity.Order;
import com.techshop.orderservice.servcie.OrderService;
import com.techshop.shared.common.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final OrderConverter converter;

    @Autowired
    public OrderController(OrderService service,  OrderConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping
    public Object getOrders() {
        try {
            List<GetOrderDto> orders = service.getOrders().stream().map(item ->   converter.toGetOrderDto(item)).collect(Collectors.toList());
            return ResponseHandler.getResponse(orders, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{order-id}")
    public Object getOrder(@PathVariable("order-id") Long orderId) {
        try {

            return ResponseHandler.getResponse(  converter.toGetOrderDto(service.getOrder(orderId)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    public Object getYourOrders() {
        try {
            List<GetOrderDto> orders = service.getYourOrders().stream().map(item -> converter.toGetOrderDto(item)).collect(Collectors.toList());
            return ResponseHandler.getResponse(orders, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cart")
    public Object getYourCart() {
        try {
            GetOrderDto cart =   converter.toGetOrderDto(service.getYourCart());
            return ResponseHandler.getResponse(cart, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/checkout")
    public Object checkOutOrder() {
        try {
            return ResponseHandler.getResponse(  converter.toGetOrderDto(service.checkout()), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/checkout/none-account")
    public Object checkOutOrderWithNoneAccount(@RequestBody OrderWithNoneAccountDto dto) {
        try {
            return ResponseHandler.getResponse( converter.toGetOrderDto(service.checkoutWithNoneAccount(dto)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/user/checkout/info")
    public Object updateInfoCheckout(@RequestBody OrderInfo orderInfo){
        try {
            service.updateInfoCheckOut(orderInfo);
            return ResponseHandler.getResponse(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @GetMapping("/report")
//    public Object orderReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
//                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
//                              @RequestParam String compression) {
//        try {
//            Map<LocalDate, List<GetOrderDto>> report = service.getOrderReport(start, end, compression)
//                    .entrySet().stream().collect(
//                            Collectors.toMap(Map.Entry::getKey,
//                                    e-> e.getValue().stream().map(GetOrderDto::new)
//                                            .collect(Collectors.toList())));
//
//            return ResponseHandler.getResponse(report, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
//        }
//    }



    @PostMapping("/add-item")
    public Object addCartItem(@RequestBody CreateOrderDetailDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);
            Order order = service.addCartItem(dto);
            GetOrderDto response =  converter.toGetOrderDto(order);
            return ResponseHandler.getResponse(response , HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove-item")
    public Object removeCartItem(@RequestBody Long variantId, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

            return ResponseHandler.getResponse(  converter.toGetOrderDto(service.deleteCartItem(variantId)), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-voucher")
    public Object addVoucher(@RequestBody HashMap<String, String> voucher, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);


            return ResponseHandler.getResponse(  converter.toGetOrderDto(service.addVoucher(voucher.get("voucherName"))), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove-voucher")
    public Object removeVoucher() {
        try {
            return ResponseHandler.getResponse(  converter.toGetOrderDto(service.removeVoucher()), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-order-status")
    public Object changeOrderStatus(@RequestBody UpdateOrderDto dto, BindingResult errors) {
        try {
            if (errors.hasErrors())
                return ResponseHandler.getResponse(errors, HttpStatus.BAD_REQUEST);

            GetOrderDto order =   converter.toGetOrderDto(service.changeOrderStatus(dto));
            return ResponseHandler.getResponse(order, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{order-id}")
    public Object deleteOrder(@PathVariable(name = "order-id") Long orderId) {
        try {
            return ResponseHandler.getResponse(service.deleteOrder(orderId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.getResponse(e, HttpStatus.BAD_REQUEST);
        }
    }
}
