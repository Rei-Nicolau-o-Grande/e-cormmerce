package com.teste.order_service.infra.dtos;

import com.teste.order_service.core.domain.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        String id,
        List<String> productsId,
        StatusOrder status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
