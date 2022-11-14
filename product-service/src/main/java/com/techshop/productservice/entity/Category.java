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
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotBlank
    private String categoryName;

    private String categoryDesc;

    private String categoryLink;

    private String imgUrl;

    private String activeFlag = "Y";

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Collection<Product> products;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private Collection<Category> children;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "category_attribute",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private Collection<Attribute> attributes = new ArrayList<>();

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
        attribute.getCategories().add(this);
    }

    public void removeAttribute(Attribute attribute){
        attributes.remove(attribute);
        attribute.getCategories().remove(this);
    }
}
