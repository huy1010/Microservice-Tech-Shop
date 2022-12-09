package com.techshop.clients.mailservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service", url = "${clients.mail-service.url}")
public interface MailServiceClient {
    @PostMapping("/send-mail")
    Object sendMail(@RequestBody MailDetailRequest request);


}
