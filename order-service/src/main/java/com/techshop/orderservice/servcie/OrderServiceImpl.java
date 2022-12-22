package com.techshop.orderservice.servcie;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.techshop.clients.productservice.ProductServiceClient;
import com.techshop.clients.productservice.UpdateVariantRequest;
import com.techshop.orderservice.config.FeignClientInterceptor;
import com.techshop.orderservice.dto.checkout.CheckoutItemDto;
import com.techshop.orderservice.dto.order.CreateOrderDetailDto;
import com.techshop.orderservice.dto.order.OrderInfo;
import com.techshop.orderservice.dto.order.OrderWithNoneAccountDto;
import com.techshop.orderservice.dto.order.UpdateOrderDto;
import com.techshop.orderservice.entity.*;
import com.techshop.orderservice.repository.OrderDetailRepository;
import com.techshop.orderservice.repository.OrderRepository;
import com.techshop.shared.common.util.AdjusterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    @Value("${BASE_URL}")
    private String baseURL;

   // @Value("${STRIPE_SECRET_KEY}")
    private String apiKey = "sk_test_51LxTbYE6RLTRdT5kJH8bn1lDtMlJlSocJ5KVnTm5cEZ5N4GIr9GLo4riUpYee6piw4g2m2z5VU1qwhnIqBImlLmi00FXFXaKos";


    private OrderRepository repository;
    private OrderDetailRepository orderDetailRepository;
    private VoucherService voucherService;
    private final ProductServiceClient _productServiceClient;


    @Autowired
    public OrderServiceImpl(OrderRepository repository,
                            OrderDetailRepository orderDetailRepository,
                            VoucherService voucherService,
                            ProductServiceClient productServiceClient) {
        this.repository = repository;
        this.orderDetailRepository = orderDetailRepository;
        this.voucherService = voucherService;
        _productServiceClient = productServiceClient;
    }

    public Long getUnitPriceVariant(Long variantId) {
        LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) _productServiceClient.getVariant(variantId);
        LinkedHashMap<String, Object> variant = (LinkedHashMap<String, Object>) response.get("content");
        Long result = Long.parseLong(variant.get("price").toString());
        return result;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = repository.findAllByIsDeletedFalse();

        return orders.stream().filter(o -> !o.getOrderStatus().equals(OrderStatus.PUTTING)).collect(Collectors.toList());
    }

    @Override
    public Order getOrder(Long orderId) {
        Optional<Order> order = repository.findById(orderId);

        if (!order.isPresent()) {
            throw new IllegalStateException("Order not exists");
        }

        return order.get();
    }

    public Order createCart() {
        Order order = new Order();
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        order.setOrderStatus(OrderStatus.PUTTING);
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setUserId(userId);

        return repository.save(order);
    }

    @Override
    public Order addCartItem(CreateOrderDetailDto dto) {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        List<Order> orderList = repository.findByUserId(userId);
        Optional<Order> cartOptional = orderList.stream().filter(o -> o.getOrderStatus().equals(OrderStatus.PUTTING)).findFirst();

        Order cart = cartOptional.orElseGet(this::createCart);

        Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(new OrderDetailPK(cart.getOrderId(), dto.getVariantId()));

        if (orderDetailOptional.isPresent()) {
            OrderDetail orderDetail = orderDetailOptional.get();
            orderDetail.setQuantity(dto.getQuantity());
            Long variantPrice = getUnitPriceVariant(orderDetail.getId().getVariantId());
            orderDetail.setUnitPrice(variantPrice);
            cart.getOrderDetails().removeIf(o -> Objects.equals(o.getId().getVariantId(), dto.getVariantId()));

            cart.getOrderDetails().add(orderDetail);

            return repository.save(cart);
        }

        OrderDetail orderDetail = new OrderDetail();
        Long variantPrice = getUnitPriceVariant(dto.getVariantId());
        orderDetail.setOrder(cart);
        orderDetail.getId().setVariantId(dto.getVariantId());
        orderDetail.setProductId(dto.getProductId());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetail.setUnitPrice(variantPrice);

        cart.getOrderDetails().add(orderDetail);

        return repository.save(cart);
    }

    @Override
    public Order deleteCartItem(Long variantId) {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        List<Order> orderList = repository.findByUserId(userId);
        Optional<Order> cartOptional = orderList.stream().filter(o -> o.getOrderStatus().equals(OrderStatus.PUTTING)).findFirst();

        if (!cartOptional.isPresent()) {
            throw new IllegalStateException("Cart not found");
        }

        Order cart = cartOptional.get();

        Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(new OrderDetailPK(cartOptional.get().getOrderId(), variantId));

        if (!orderDetailOptional.isPresent()) {
            throw new IllegalStateException("Cart item not found");
        }

        OrderDetail orderDetail = orderDetailOptional.get();

        cart.getOrderDetails().remove(orderDetail);

        orderDetailRepository.delete(orderDetail);
        return repository.save(cart);
    }

    @Override
    public Order addVoucher(String voucherName) {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        List<Order> orderList = repository.findByUserId(userId);
        Optional<Order> cartOptional = orderList.stream().filter(o -> o.getOrderStatus().equals(OrderStatus.PUTTING)).findFirst();

        Order cart = cartOptional.orElseGet(this::createCart);

        Voucher voucher = voucherService.getVoucherByName(voucherName);

        cart.setVoucher(voucher);

        return repository.save(cart);
    }

    @Override
    public Order removeVoucher() {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        List<Order> orderList = repository.findByUserId(userId);
        Optional<Order> cartOptional = orderList.stream().filter(o -> o.getOrderStatus().equals(OrderStatus.PUTTING)).findFirst();

        Order cart = cartOptional.orElseGet(this::createCart);

        cart.setVoucher(null);

        return repository.save(cart);
    }

    @Override
    public List<Order> getYourOrders() {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        return repository.findByUserId(userId).stream().filter(o -> !o.getOrderStatus().equals(OrderStatus.PUTTING)).collect(Collectors.toList());

    }

    @Override
    public Order getYourCart() {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        System.out.println(userId);
        List<Order> orderList = repository.findByUserId(userId);
        Optional<Order> cartOptional = orderList.stream().filter(o -> o.getOrderStatus().equals(OrderStatus.PUTTING)).findFirst();
        Order cart = cartOptional.orElse(null);

        if (cart == null) {
            throw new IllegalStateException("Cart not found!");
        }

        return cart;
    }

    @Override
    public Order checkout(boolean isPaid) throws Exception {
        Order cart = getYourCart();

        if (cart.getVoucher() != null) {
            Integer voucherAmount = cart.getVoucher().getAmount();
            cart.getVoucher().setAmount(voucherAmount - 1);
        }
        // check quantity variant
        LinkedHashMap<String, Object> res = (LinkedHashMap<String, Object>) _productServiceClient.checkEnoughVariantQuantity(cart.getOrderDetails().stream().map(
                detail -> {
                    UpdateVariantRequest request = new UpdateVariantRequest();
                    request.setQuantity(detail.getQuantity());
                    request.setVariantId(detail.getId().getVariantId());
                    return request;
                }
        ).collect(Collectors.toList()));
        if(res.get("content") != "") {
            throw  new Exception((String) res.get("content"));
        }
        // to do update variant quantity
        for (OrderDetail detail : cart.getOrderDetails()) {
            LinkedHashMap<String, Object> response = null;
            try {
                UpdateVariantRequest request = new UpdateVariantRequest();
                request.setQuantity(detail.getQuantity());
                request.setVariantId(detail.getId().getVariantId());

                request.setMethod("sub");
                response = (LinkedHashMap<String, Object>) _productServiceClient.updateVariantForSell(request);
                System.out.println(response);
            } catch (Exception e) {
                System.out.println(e);
                res = (LinkedHashMap<String, Object>) _productServiceClient.getVariant(detail.getId().getVariantId());
                LinkedHashMap<String, Object> variant = (LinkedHashMap<String, Object>) res.get("content");
                String result = variant.get("variantName").toString();
                throw new Exception("Số lượng " + result + " trong kho không đủ");
            }
        }

        if(isPaid) {
            cart.setPaymentStatus(PaymentStatus.PAID);
        }
        cart.setOrderStatus(OrderStatus.PENDING);
        cart.setCreatedAt(LocalDateTime.now());

        return repository.save(cart);
    }

    @Override
    public Order changeOrderStatus(UpdateOrderDto dto) {
        Order order = getOrder(dto.getOrderId());

        if (dto.getOrderStatus() == OrderStatus.CANCELED) {
            if (order.getOrderStatus() == OrderStatus.PENDING) {
              order.getOrderDetails().forEach(detail -> {
                          UpdateVariantRequest request = new UpdateVariantRequest();
                          request.setQuantity(detail.getQuantity());
                          request.setVariantId(detail.getId().getVariantId());
                          request.setMethod("add");
                          _productServiceClient.updateVariantForSell(request);
                      }
               );
            }
        }

        order.setOrderStatus(dto.getOrderStatus());
        order.setPaymentStatus(dto.getPaymentStatus());


        return repository.save(order);
    }

    @Override
    public Map<LocalDate, List<Order>> getOrderReport(LocalDate start, LocalDate end, String compression) {
        return repository.findByCreatedAtBetweenOrderByCreatedAt(start.atStartOfDay(), end.atStartOfDay())
                .stream().filter(o -> o.getPaymentStatus().equals(PaymentStatus.PAID) && o.getIsDeleted() == false)
                .collect(Collectors.groupingBy(item -> item.getCreatedAt().toLocalDate()
                        .with(AdjusterUtils.getAdjuster().get(compression))));
    }


    @Override
    public Boolean deleteOrder(Long orderId) {
        Order order = getOrder(orderId);

        order.setIsDeleted(true);
        repository.save(order);

        return true;
    }

    @Override
    public void updateInfoCheckOut(OrderInfo orderInfo) {
        Long userId = Long.parseLong(FeignClientInterceptor.getClaims().get("jti").toString());
        List<Order> orderList = repository.findByUserId(userId);
        Order order = orderList.stream().filter(o -> o.getOrderStatus().equals(OrderStatus.PUTTING)).findFirst().get();

        order.setDeliveryAddress(orderInfo.getDeliveryAddress());
        order.setRecipientName(orderInfo.getRecipientName());
        order.setPhoneNumber(orderInfo.getPhoneNumber());

        repository.save(order);
    }

    @Transactional
    @Override
    public Order checkoutWithNoneAccount(OrderWithNoneAccountDto dto) throws Exception {
        Order order = new Order();
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setRecipientName(dto.getRecipientName());
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setOrderStatus(OrderStatus.PENDING);

        Set<OrderDetail> orderDetail = new HashSet<>();
        dto.getOrderDetail().forEach(item -> {
            OrderDetail detail = new OrderDetail();
            Long variantPrice = getUnitPriceVariant(item.getVariantId());
            detail.getId().setVariantId(item.getVariantId());
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(variantPrice);
            detail.setOrder(order);

            orderDetail.add(detail);
        });

        order.setOrderDetails(orderDetail);
        order.setCreatedAt(LocalDateTime.now());
        // check quantity variant
        LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) _productServiceClient.checkEnoughVariantQuantity(order.getOrderDetails().stream().map(
                detail -> {
                    UpdateVariantRequest request = new UpdateVariantRequest();
                    request.setQuantity(detail.getQuantity());
                    request.setVariantId(detail.getId().getVariantId());
                    return request;
                }
        ).collect(Collectors.toList()));
        if(response.get("content") != "") {
            throw  new Exception((String) response.get("content"));
        }
        // to do update variant quantity
        for (OrderDetail detail : order.getOrderDetails()) {
            try {
                UpdateVariantRequest request = new UpdateVariantRequest();
                request.setQuantity(detail.getQuantity());
                request.setVariantId(detail.getId().getVariantId());
                request.setMethod("sub");
                response = (LinkedHashMap<String, Object>) _productServiceClient.updateVariantForSell(request);
            } catch (Exception e) {
                System.out.println(e);
                LinkedHashMap<String, Object> res = (LinkedHashMap<String, Object>) _productServiceClient.getVariant(detail.getId().getVariantId());
                LinkedHashMap<String, Object> variant = (LinkedHashMap<String, Object>) res.get("content");
                String result = variant.get("variantName").toString();
                throw new Exception("Số lượng " + result + " trong kho không đủ");
            }
        }



        return repository.save(order);
    }

    @Override
    public Object getRevenue() {
        List<Order> orders = repository.findAllByIsDeletedFalse().stream().filter(o -> o.getPaymentStatus() == PaymentStatus.PAID).collect(Collectors.toList());
        return new HashMap<String, Object>() {{
            put("count_paid_orders", orders.size());
            put("revenue", orders.stream().map(Order::getTotalPrice).mapToLong(Long::longValue).sum());
        }};
    }
    @Override
    public Object getBestSeller() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll().stream().filter(o -> o.getOrder().getOrderStatus().equals(OrderStatus.COMPLETED)).collect(Collectors.toList());
        List<Map<String, Object>> sold = new ArrayList<>();
        LinkedHashMap<Long, Integer> sorted = new LinkedHashMap<>();

       Map<Long, Integer> calc = orderDetails.stream().collect(Collectors
                .groupingBy(OrderDetail::getProductId,
                        Collectors.summingInt(OrderDetail::getQuantity)));

        calc.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

        int[] index = {0};
        sorted.forEach((aLong, integer) -> {
            int currentIndex = index[0];
            if (currentIndex == 4)
                return;
            Map<String, Object> temp = new HashMap<>();
            LinkedHashMap<String, Object> res = (LinkedHashMap<String, Object>) _productServiceClient.getProductById(aLong);
            LinkedHashMap<String, Object> product = (LinkedHashMap<String, Object>) res.get("content");
            temp.put("product",product);
            temp.put("quantity_sold", integer);

            sold.add(temp);
            index[0]++;
        });

        return sold;
    }
    @Override
    public String createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        // sucess and failure urls

        String successURL = baseURL + "payment/success";

        String failureURL = baseURL + "payment/failure";

        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();


        for (CheckoutItemDto checkoutItemDto: checkoutItemDtoList) {
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        }

        SessionCreateParams.Builder params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .addAllLineItem(sessionItemList)
                .setSuccessUrl(successURL);

        if(!Objects.equals(checkoutItemDtoList.get(0).getVoucherId(), null))
        params.addDiscount(
                SessionCreateParams.Discount.builder()
                        .setCoupon(checkoutItemDtoList.get(0).getVoucherId())
                        .build()
        );
        return Session.create(params.build()).getId();
    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {

        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();

    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("vnd")
                .setUnitAmount((long)(checkoutItemDto.getPrice()))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                ).build();
    }

}
