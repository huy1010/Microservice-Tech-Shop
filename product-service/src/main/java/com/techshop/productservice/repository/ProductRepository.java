package com.techshop.productservice.repository;

import com.techshop.productservice.dto.product.ProductDetailDto;
import com.techshop.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT distinct p FROM Product p   WHERE p.activeFlag = :activeFlag ")
    List<Product> findByActiveFlag(String activeFlag);

    @Query("SELECT p FROM Product p WHERE p.activeFlag = 'Y'  ")
    ProductDetailDto getProductDetail(Long productId);

    Product findByProductLink(String productLink);

    @Query("SELECT p FROM Product p WHERE p.activeFlag = 'Y' AND p.category.categoryLink = :categoryLink")
    List<Product> findByCategoryLink(String categoryLink);

    @Query("SELECT p FROM Product p WHERE p.activeFlag = 'Y' AND p.brand.brandName = :brandName")
    List<Product> findByBrandName(String brandName);
}


//@Repository
//public interface ProductRepository extends JpaRepository<Product, Long> {
//
//    @Query("SELECT  p FROM Product p Where p.name =:name ")
//    Page<Product> findByName(@Param("name") String name, Pageable pageable);
//}
//
//














