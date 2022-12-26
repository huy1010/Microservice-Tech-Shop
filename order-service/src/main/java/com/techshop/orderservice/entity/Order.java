package com.techshop.orderservice.entity;

import com.techshop.shared.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "orders")
public class Order  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PUTTING;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Transient
    private Long totalPrice;

    public Long getTotalPrice() {
        return orderDetails.stream().mapToLong(i -> i.getUnitPrice() * i.getQuantity()).sum();
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Column(name = "user_id")
    private Long userId;

    private String phoneNumber;
    private String recipientName;
    private String deliveryAddress;
    private String email;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

}
