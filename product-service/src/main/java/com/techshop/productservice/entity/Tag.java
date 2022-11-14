package com.techshop.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String tagName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @JsonIgnore
    @OneToMany(mappedBy = "tag")
    private List<VariantAttribute> variantAttributes;
}
