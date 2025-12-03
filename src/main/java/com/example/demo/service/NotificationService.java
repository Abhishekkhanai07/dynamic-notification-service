package com.example.demo.service;

import com.example.demo.dto.NotificationRequest;
import com.example.demo.entity.MailConfig;
import com.example.demo.repository.MailConfigRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NotificationService {

    private final MailConfigRepository mailConfigRepository;

    public NotificationService(MailConfigRepository mailConfigRepository) {
        this.mailConfigRepository = mailConfigRepository;
    }

    public void sendNotification(NotificationRequest request) throws MessagingException {

        MailConfig config = mailConfigRepository.findFirstByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No config found"));

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(config.getHost());
        sender.setPort(config.getPort());
        sender.setUsername(config.getUsername());
        sender.setPassword(config.getPassword());

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", config.getUseTls());
        props.put("mail.smtp.ssl.enable", config.getUseSsl());

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(request.getTomail());
        helper.setFrom(config.getFromEmail());
        helper.setSubject(request.getSubject());
        helper.setText(request.getBody(), false);

        sender.send(message);
    }
}
