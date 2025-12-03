package com.example.demo.controller;

import com.example.demo.dto.NotificationRequest;
import com.example.demo.service.NotificationService;

import jakarta.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request)
            throws MessagingException {

        notificationService.sendNotification(request);
        return ResponseEntity.ok("Email sent successfully!");
    }
}
