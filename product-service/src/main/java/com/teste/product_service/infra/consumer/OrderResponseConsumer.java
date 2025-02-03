package com.teste.product_service.infra.consumer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseConsumer(
        String id,
        List<String> productsId,
        StatusOrder status,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
