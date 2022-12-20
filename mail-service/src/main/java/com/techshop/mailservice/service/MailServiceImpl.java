package com.techshop.mailservice.service;

import com.techshop.mailservice.entity.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender _javaMailSender;

    @Value("${spring.mail.from}")
    private String sender;


    public MailServiceImpl(JavaMailSender javaMailSender) {
        this._javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            // Creating a simple mail message
            MimeMessage mimeMessage = _javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;

            mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(new InternetAddress("no-reply@gearshop.com", sender));
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Sending the mail
            _javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage = _javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            // Setting multipart as true for attachments to be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(new InternetAddress("no-reply@gearshop.com", sender));
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            // Sending the mail
            _javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (Exception e) {
            return "Error while sending mail!!!";
        }
    }

    @Override
    public String sendHtmlMail(EmailDetails details) {
        return null;
    }
}
