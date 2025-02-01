package com.teste.product_service.infra.dtos;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        Integer stock
) {
}
