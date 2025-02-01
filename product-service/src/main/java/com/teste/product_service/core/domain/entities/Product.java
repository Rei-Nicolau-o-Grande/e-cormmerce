package com.teste.product_service.core.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Product(
        String id,
        String name,
        String description,
        BigDecimal price,
        String code,
        Integer stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deactivatedAt,
        Boolean active

) {
}
