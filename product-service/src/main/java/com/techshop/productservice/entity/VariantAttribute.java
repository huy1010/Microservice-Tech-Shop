package com.techshop.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techshop.productservice.key.VariantAttributeKey;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class VariantAttribute implements Serializable {

    @JsonIgnore
    @EmbeddedId
    private VariantAttributeKey id = new VariantAttributeKey();

    @JsonIgnore
    @ManyToOne
    @MapsId("variantId")
    @JoinColumn(name = "variant_id")
    private Variant variant;

    @JsonIgnore
    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    private String value;
    private String description;

    @ManyToOne
    @JoinColumn(name ="tag_id")
    private Tag tag;
}
