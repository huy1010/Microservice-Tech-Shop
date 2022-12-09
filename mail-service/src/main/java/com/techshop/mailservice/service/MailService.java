package com.techshop.mailservice.service;

import com.techshop.mailservice.entity.EmailDetails;

public interface MailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}
