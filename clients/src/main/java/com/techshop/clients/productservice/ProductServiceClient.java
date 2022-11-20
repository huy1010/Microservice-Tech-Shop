package com.techshop.clients.productservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service", url = "${clients.product-service.url}")
public interface ProductServiceClient {

    @GetMapping("api/brands")
    Object getBrands(@RequestParam("onlyActive") Boolean isActive );
}
