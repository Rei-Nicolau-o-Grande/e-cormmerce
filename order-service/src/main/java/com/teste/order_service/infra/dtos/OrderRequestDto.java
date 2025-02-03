package com.teste.order_service.infra.dtos;

import java.util.List;

public record OrderRequestDto(
        List<String> productsId
) {
}
