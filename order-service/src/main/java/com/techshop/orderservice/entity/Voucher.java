package com.techshop.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techshop.shared.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"voucher_name"})})
public class Voucher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "voucher_name",
            length = 100)
    private String voucherName;

    @Column(name = "voucher_desc",
            length = 100)
    private String voucherDesc;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "valid_date")
    private LocalDateTime validDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "voucher_value")
    private Integer voucherValue;

    @Column(name = "capped_at")
    private Long cappedAt;

    @Column(name = "is_deleted",
            columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @JsonIgnore
    @OneToMany(mappedBy = "voucher")
    @Column(name = "order_id")
    private Set<Order> orders;
}
