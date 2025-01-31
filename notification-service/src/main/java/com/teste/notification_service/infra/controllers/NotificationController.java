package com.teste.notification_service.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testNotificationService() {
        return ResponseEntity.ok(Map.of("message", "Notification service is working!"));
    }
}
