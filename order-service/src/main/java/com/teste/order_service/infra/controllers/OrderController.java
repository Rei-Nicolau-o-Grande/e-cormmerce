package com.teste.order_service.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testOrderService() {
        Map<String, String> response = Map.of("message", "Order service is working.");
        return ResponseEntity.ok(response);
    }
}
