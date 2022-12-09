package com.techshop.mailservice.controller;

import com.techshop.mailservice.entity.EmailDetails;
import com.techshop.mailservice.service.MailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class EmailController {
    private final MailService _emailService;

    public EmailController(MailService emailService) {
        this._emailService = emailService;
    }

    @PostMapping("/send-mail")
    public String
    sendMail(@RequestBody EmailDetails details) {
        String status = _emailService.sendSimpleMail(details);
        return status;
    }

    @PostMapping("/send-mail-with-attachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        String status = _emailService.sendMailWithAttachment(details);
        return status;
    }
}