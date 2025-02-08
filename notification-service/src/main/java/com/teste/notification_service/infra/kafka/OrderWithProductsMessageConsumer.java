package com.teste.notification_service.infra.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record OrderWithProductsMessageConsumer(
        String id,
        List<ProductMessageConsume> products,
        StatusOrder status,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
