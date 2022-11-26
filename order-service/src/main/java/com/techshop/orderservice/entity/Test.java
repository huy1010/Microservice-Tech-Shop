package com.techshop.orderservice.entity;

import com.techshop.shared.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "techshop_test")
public class Test extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", updatable = false)
    private Long testId;

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 50)
    private String testDesc;
}
