package com.techshop.productservice.service;

import com.techshop.productservice.common.util.StringUtils;
import com.techshop.productservice.converter.ProductConverter;
import com.techshop.productservice.dto.product.ProductDto;
import com.techshop.productservice.dto.product.ProductWithVariantDto;
import com.techshop.productservice.entity.Brand;
import com.techshop.productservice.entity.Category;
import com.techshop.productservice.entity.Product;
import com.techshop.productservice.repository.ProductCriteriaRepository;
import com.techshop.productservice.repository.ProductRepository;
import com.techshop.productservice.search.ProductSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repo;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService cateService;

    @Autowired
    private ProductConverter converter;

    @Autowired
    private ProductCriteriaRepository  criteriaRepository;
    

    @Override
    public List<ProductWithVariantDto> getProducts() {
        List<Product> products = repo.findByActiveFlag("Y");
        List<ProductWithVariantDto> result = new ArrayList<>();

        products.forEach(product -> result.add(converter.toProductWithVariant(product)));

        return result;
    }

    @Override
    public Object getAll(ProductSearchCriteria searchCriteria) {

        return criteriaRepository.findAllWithFilters(searchCriteria);
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = repo.findById(id);
        if(!product.isPresent() )
            throw new IllegalStateException("Product with productId " + id +" is not existed");

        if(product.get().getActiveFlag().equals("D"))
            throw new IllegalStateException("Product with productId " + id +" is deleted");

        return product.get();
    }

    @Override
    public ProductWithVariantDto createProduct(ProductDto dto) {
        Product product = handleData(dto, false);
        Product result = repo.save(product);
        return converter.toProductWithVariant(result);
    }

    @Override
    public ProductWithVariantDto updateProduct(ProductDto dto) {
        Product product = handleData(dto, true);
        Product result = repo.save(product);
        return converter.toProductWithVariant(result);
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = getProductById(productId);
        product.setActiveFlag("D");
        repo.save(product);
        return true;
    }

    @Override
    public ProductWithVariantDto getProductDetailById(Long productId) {

        Product product = getProductById(productId);
        return converter.toProductWithVariant(product);
    }

    @Override
    public ProductWithVariantDto getByProductLink(String productLink) {
        Product product = repo.findByProductLink(productLink);
        return converter.toProductWithVariant(product);
    }

    @Override
    public List<ProductWithVariantDto> getProductByCategoryLink(String categoryLink) {
        List<Product> products = repo.findByCategoryLink(categoryLink);
        List<ProductWithVariantDto> result = new ArrayList<>();
        products.forEach(product -> result.add(converter.toProductWithVariant(product)));
        return result;
    }

    @Override
    public List<ProductWithVariantDto> getProductByBrand(String brandName) {
        List<Product> products = repo.findByBrandName(brandName);
        List<ProductWithVariantDto> result = new ArrayList<>();
        products.forEach(product -> result.add(converter.toProductWithVariant(product)));
        return result;
    }


    public Product handleData(ProductDto dto, boolean hasId){
        Product product = new Product();

        if(hasId)
            product = repo.getById(dto.getProductId());


        if(dto.getBrandId() != null) {
            Brand brand = brandService.getBrandById(dto.getBrandId());
            product.setBrand(brand);
        }

        if(dto.getCategoryId() != null) {
            Category category = cateService.getCategoryById(dto.getCategoryId());
            product.setCategory(category);
        }

        if(dto.getProductName() != null){
            product.setProductName(dto.getProductName());
            String productLink = StringUtils.deAccent(dto.getProductName());
            product.setProductLink(productLink);
        }


        if(dto.getProductDesc() != null)
            product.setProductDesc(dto.getProductDesc());

        if(dto.getImgUrl() != null)
            product.setImgUrl(dto.getImgUrl());

        return  product;
    }


    /* */
//    @Override
//    public ProductDetailDto getVariantsByProductId(Long productId) {
////        ProductDetailDto response = new ProductDetailDto();
////        Product product = getProductById(productId);
////
////        response.setProductDesc(product.getProductDesc());
////        response.setProductName(product.getProductName());
////        response.setProductId(product.getProductId());
////        response.setCategory(product.getCategory());
////        response.setBrand(product.getBrand());
////
//////        List<Variant> variants = variantService.getByProductId(productId);
////        response.setVariants(variants);
////        return response;
//        return repo.findByActiveFlag("Y")
//    }
}
