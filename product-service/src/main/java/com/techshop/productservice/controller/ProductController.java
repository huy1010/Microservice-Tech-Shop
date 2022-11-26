package com.techshop.productservice.controller;

import com.techshop.productservice.common.ResponseHandler;
import com.techshop.productservice.dto.product.ProductDto;
import com.techshop.productservice.dto.product.ProductWithVariantDto;
import com.techshop.productservice.search.ProductSearchCriteria;
import com.techshop.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping()
public class ProductController {

    private  ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/website/products/link/{product-link}")
    public Object getByProductLink(@PathVariable(value = "product-link") String productLink) {
        try{
            ProductWithVariantDto products = productService.getByProductLink(productLink);
            return ResponseHandler.getResponse(products, HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/website/categories/link/{category-link}")
    public Object getProductByCategoryLink(@PathVariable("category-link") String categoryLink){
        try{
            return ResponseHandler.getResponse(productService.getProductByCategoryLink(categoryLink), HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/website/brands/{brand-name}")
    public Object getProductByBrand(@PathVariable("brand-name") String brandName){
        try{
            return ResponseHandler.getResponse(productService.getProductByBrand(brandName), HttpStatus.OK);
        } catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/products/search")
    public Object getProducts(ProductSearchCriteria productSearchCriteria) {
//            List<ProductWithVariantDto> products = productService.getProducts().stream().map(ProductWithVariantDto::new).collect(Collectors.toList());
            Object  products = productService.getAll(productSearchCriteria);
            return ResponseHandler.getResponse(products, HttpStatus.OK);

    }


    @GetMapping("/products")
    public Object getProducts(@RequestParam(value = "onlyActive") Boolean isActive) {
        if(isActive){
//            List<ProductWithVariantDto> products = productService.getProducts().stream().map(ProductWithVariantDto::new).collect(Collectors.toList());
            List<ProductWithVariantDto> products = productService.getProducts();
            return ResponseHandler.getResponse(products, HttpStatus.OK);
        }

        return ResponseHandler.getResponse(HttpStatus.OK);
    }

    @GetMapping(path = "/products/{product-id}")
    public Object getProductById(@PathVariable("product-id") Long productId){
        try{
            ProductWithVariantDto product = productService.getProductDetailById(productId);
            return ResponseHandler.getResponse(product, HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @GetMapping(path = "/{product-id}/variants")
//    public Object getProductDetail(@PathVariable("product-id") Long productId){
//        try{
//            return ResponseHandler.getResponse(productService.getVariantsByProductId(productId), HttpStatus.OK);
//        }catch (Exception e){
//            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

//    @GetMapping(path = "/{product-id}/variants/{variant-id}")
//    public Object getVariantDetail(@PathVariable("product-id" ) Long productId, @PathVariable("variant-id") Long variantId){
//        try{
//            return ResponseHandler.getResponse(productService.getVariantDetailByProductId(productId, variantId), HttpStatus.OK);
//        }catch (Exception e){
//            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @PostMapping("/products")
    public Object createProduct(@Valid @RequestBody ProductDto newProduct, BindingResult errors){
        try{
            return ResponseHandler.getResponse(productService.createProduct(newProduct), HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products")
    public Object updateProduct(@RequestBody ProductDto updatedProduct, BindingResult errors){
        try{
            return ResponseHandler.getResponse(productService.updateProduct(updatedProduct), HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{product-id}")
    public Object deleteProduct(@PathVariable("product-id") Long productId){
        try{
            return ResponseHandler.getResponse(productService.deleteProduct(productId), HttpStatus.OK);
        }catch (Exception e){
            return ResponseHandler.getResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
