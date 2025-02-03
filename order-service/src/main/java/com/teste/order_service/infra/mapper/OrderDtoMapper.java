package com.teste.order_service.infra.mapper;

import com.teste.order_service.core.domain.entities.Order;
import com.teste.order_service.infra.dtos.OrderRequestDto;
import com.teste.order_service.infra.dtos.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {

    public static OrderResponseDto toDtoResponse(Order order) {
        return new OrderResponseDto(
                order.id(),
                order.productsId(),
                order.status(),
                order.createdAt(),
                order.updatedAt()
        );
    }

    public static Order toDomain(OrderRequestDto orderRequestDto) {
        return new Order(
                null,
                orderRequestDto.productsId(),
                null,
                null,
                null
        );
    }
}
