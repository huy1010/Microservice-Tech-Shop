package com.techshop.productservice.dto;

import com.techshop.productservice.dto.attribute.AttributeDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDto {
    private Long categoryId;

    @NotEmpty
    @NotBlank
    private String categoryName;
    private String categoryDesc;
    private String categoryLink;
    private Long parentId;
    private String imgUrl;

    private List<AttributeDto> attributes = new ArrayList<>();
}
