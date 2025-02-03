package com.teste.order_service.infra.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teste.order_service.core.domain.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        String id,
        List<String> productsId,
        StatusOrder status,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt
) {
}
