package com.teste.order_service.core.usecase;

import com.teste.order_service.core.domain.entities.Order;

public interface CreateOrderUseCase {
    Order execute(Order order);
}
