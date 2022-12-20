package com.techshop.clients.mailservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDetailRequest {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
