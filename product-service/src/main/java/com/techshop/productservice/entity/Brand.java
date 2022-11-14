package com.techshop.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techshop.productservice.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Brand extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @NotBlank
    private String brandName;

    private String imgUrl;
    private String brandDesc;

    private String activeFlag = "Y" ;

    @JsonIgnore
    @OneToMany(  mappedBy = "brand")
    private Collection<Product> products;
}
