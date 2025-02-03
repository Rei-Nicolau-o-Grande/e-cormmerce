package com.teste.order_service.core.domain.entities;

import com.teste.order_service.core.domain.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
        String id,
        List<String> productsId,
        StatusOrder status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
