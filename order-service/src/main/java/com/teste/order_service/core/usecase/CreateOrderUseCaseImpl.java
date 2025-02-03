package com.teste.order_service.core.usecase;

import com.teste.order_service.core.domain.entities.Order;
import com.teste.order_service.core.gateway.OrderGateway;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderGateway orderGateway;

    public CreateOrderUseCaseImpl(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public Order execute(Order order) {
        return orderGateway.createOrder(order);
    }
}
