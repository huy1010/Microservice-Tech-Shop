package io.github.tubean.eureka.clients.image;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("image-service")
public interface ImageClient {
    @GetMapping(path = "images")
    List<Object> getImages();
}
