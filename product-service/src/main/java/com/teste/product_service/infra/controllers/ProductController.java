package com.teste.product_service.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testProductService() {
        Map<String, String> response = Map.of("message", "Product service is working.");
        return ResponseEntity.ok(response);
    }
}
