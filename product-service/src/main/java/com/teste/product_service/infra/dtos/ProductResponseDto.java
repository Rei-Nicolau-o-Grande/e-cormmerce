package com.teste.product_service.infra.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        String id,
        String name,
        String description,
        BigDecimal price,
        String code,
        Integer stock,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime deactivatedAt,

        Boolean active
) {
}
