package com.techshop.productservice.key;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class VariantAttributeKey implements Serializable {
    @Column(name = "variant_id")
    private Long variantId;

    @Column(name = "attribute_id")
    private Long attributeId;
}
