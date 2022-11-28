package com.techshop.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders_detail")
public class OrderDetail {
    @JsonIgnore
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private OrderDetailPK id = new OrderDetailPK();

    @JsonIgnore
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity",
            nullable = false)
    private Integer quantity;

    @Column(name = "unit_price")
    private Long unitPrice;
}
