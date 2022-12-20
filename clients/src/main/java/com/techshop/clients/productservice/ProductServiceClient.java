package com.techshop.clients.productservice;

import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${clients.product-service.url}")
public interface ProductServiceClient {

    @GetMapping("api/brands")
    Object getBrands(@RequestParam("onlyActive") Boolean isActive );

    @GetMapping("products/{product-id}")
    Object getProductById(@PathVariable("product-id") Long productId);

    @GetMapping("variants/{variant-id}")
    Object getVariant(@PathVariable("variant-id") Long variantId);

    @PutMapping("variants/check-enough-variant-quantity")
    Object checkEnoughVariantQuantity(@RequestBody List<UpdateVariantRequest> requests);

    @GetMapping("variants/{variant-id}/exists")
    Boolean existsVariant(@PathVariable("variant-id") Long variantId);

    @PutMapping("variants/inventories")
    void updateVariantInventory(@RequestBody List<UpdateVariantRequest> requests);

    @PutMapping("variants/update-variant-for-sell")
    Object updateVariantForSell(@RequestBody UpdateVariantRequest requests);
}
