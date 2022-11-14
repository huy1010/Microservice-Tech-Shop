package com.techshop.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techshop.productservice.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name",
            unique = true)
    private String productName;

    @Column(name = "product_desc", length = 1000)
    private String productDesc;

    private String productLink;
    private String imgUrl;

    @Column(columnDefinition = ("varchar(1) default 'Y'"))
    private String activeFlag = "Y";

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Variant> variants = new HashSet<>();


}
