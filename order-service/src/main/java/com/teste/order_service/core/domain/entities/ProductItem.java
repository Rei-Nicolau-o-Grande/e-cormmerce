package com.teste.order_service.core.domain.entities;

import java.math.BigDecimal;

public record ProductItem(
        String id,
        String name,
        String description,
        BigDecimal price,
        String code
) {
}
