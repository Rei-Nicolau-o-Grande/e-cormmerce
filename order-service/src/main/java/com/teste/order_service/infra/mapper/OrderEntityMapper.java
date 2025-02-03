package com.teste.order_service.infra.mapper;

import com.teste.order_service.core.domain.entities.Order;
import com.teste.order_service.infra.persistence.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityMapper {

    public static OrderEntity toEntity(Order order) {
        return new OrderEntity(
                order.id(),
                order.productsId(),
                order.status(),
                order.createdAt(),
                order.updatedAt()
        );
    }

    public static Order toDomain(OrderEntity orderEntity) {
        return new Order(
                orderEntity.getId(),
                orderEntity.getProductsId(),
                orderEntity.getStatus(),
                orderEntity.getCreatedAt(),
                orderEntity.getUpdatedAt()
        );
    }
}
