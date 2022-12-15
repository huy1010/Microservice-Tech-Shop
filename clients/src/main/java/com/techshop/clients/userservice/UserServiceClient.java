package com.techshop.clients.userservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "user-service", url = "${clients.user-service.url}")
public interface UserServiceClient {
    @GetMapping(path = "/profile/me")
    Object getProfile(@RequestHeader("Authorization") String token);
}
