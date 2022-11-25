package com.techshop.clients.productservice;

import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${clients.product-service.url}")
public interface ProductServiceClient {

    @GetMapping("api/brands")
    Object getBrands(@RequestParam("onlyActive") Boolean isActive );

    @GetMapping("variants/{variant-id}/exists")
    Boolean existsVariant(@PathVariable("variant-id") Long variantId);

    @PutMapping("variants/inventories")
    void updateVariantInventory(@RequestBody List<UpdateVariantRequest> requests);
}
