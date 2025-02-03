package com.teste.order_service.infra.gateway;

import com.teste.order_service.core.domain.entities.Order;
import com.teste.order_service.core.gateway.OrderGateway;
import com.teste.order_service.infra.mapper.OrderEntityMapper;
import com.teste.order_service.infra.persistence.OrderEntity;
import com.teste.order_service.infra.persistence.OrderRepository;

public class OrderInfraGateway implements OrderGateway {

    private final OrderRepository orderRepository;

    public OrderInfraGateway(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = OrderEntityMapper.toEntity(order);
        return OrderEntityMapper.toDomain(orderRepository.save(orderEntity));
    }
}
