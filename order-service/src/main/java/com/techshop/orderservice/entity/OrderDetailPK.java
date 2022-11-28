package com.techshop.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailPK implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "variant_id")
    private Long variantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailPK that = (OrderDetailPK) o;
        return orderId.equals(that.orderId) && variantId.equals(that.variantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, variantId);
    }
}
