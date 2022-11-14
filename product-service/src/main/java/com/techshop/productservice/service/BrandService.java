package com.techshop.productservice.service;


import com.techshop.productservice.dto.brand.BrandDto;
import com.techshop.productservice.entity.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getBrands();
    List<Brand> getBrandsActive();
    Brand getBrandById(Long brandId);
    Brand createBrand(BrandDto dto);
    Brand updateBrand(BrandDto dto);
    Boolean deleteBrand(Long brandId);
}
