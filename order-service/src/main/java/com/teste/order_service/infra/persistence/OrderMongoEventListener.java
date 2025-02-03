package com.teste.order_service.infra.persistence;

import com.teste.order_service.core.domain.enums.StatusOrder;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import java.time.LocalDateTime;

public class OrderMongoEventListener extends AbstractMongoEventListener<OrderEntity> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<OrderEntity> event) {
        OrderEntity orderEntity = event.getSource();
        if (orderEntity.getId() == null) {
            orderEntity.setStatus(StatusOrder.CREATED);
            orderEntity.setCreatedAt(LocalDateTime.now());
            orderEntity.setUpdatedAt(LocalDateTime.now());
        } else {
            orderEntity.setUpdatedAt(LocalDateTime.now());
        }
    }
}
