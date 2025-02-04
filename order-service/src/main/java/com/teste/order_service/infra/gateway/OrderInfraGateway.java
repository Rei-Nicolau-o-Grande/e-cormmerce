package com.teste.order_service.infra.gateway;

import com.teste.order_service.core.domain.entities.Order;
import com.teste.order_service.core.gateway.OrderGateway;
import com.teste.order_service.infra.exceptions.EntityNotFoundException;
import com.teste.order_service.infra.kafka.OrderMessageConsumer;
import com.teste.order_service.infra.mapper.OrderDtoMapper;
import com.teste.order_service.infra.mapper.OrderEntityMapper;
import com.teste.order_service.infra.persistence.OrderEntity;
import com.teste.order_service.infra.persistence.OrderRepository;
import com.teste.order_service.infra.kafka.producer.OrderProducer;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class OrderInfraGateway implements OrderGateway {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderInfraGateway.class);

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderInfraGateway(OrderRepository orderRepository,
                             OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = OrderEntityMapper.toEntity(order);
        orderRepository.save(orderEntity);
        Order orderResponseDto = OrderEntityMapper.toDomain(orderEntity);
        orderProducer.sendOrderTopicCreated(OrderDtoMapper.toDtoResponse(orderResponseDto));
        log.info("Order created");
        return orderResponseDto;
    }

    public void updateOrderStatus(OrderMessageConsumer orderMessageConsumer) {
        OrderEntity orderEntity = orderRepository.findById(orderMessageConsumer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        orderEntity.setStatus(orderMessageConsumer.getStatus());
        orderEntity.setUpdatedAt(orderMessageConsumer.getUpdatedAt());
        orderRepository.save(orderEntity);
        log.info("Order status updated: ID: {}, Status: {}", orderEntity.getId(), orderEntity.getStatus());
    }
}
