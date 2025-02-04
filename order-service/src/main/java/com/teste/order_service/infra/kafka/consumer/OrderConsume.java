package com.teste.order_service.infra.kafka.consumer;

import com.teste.order_service.infra.gateway.OrderInfraGateway;
import com.teste.order_service.infra.kafka.OrderMessageConsumer;
import com.teste.order_service.infra.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsume {

    private static final Logger log = LoggerFactory.getLogger(OrderConsume.class);

    private final JsonUtil jsonUtil;
    private final OrderInfraGateway orderInfraGateway;

    public OrderConsume(JsonUtil jsonUtil,
                        OrderInfraGateway orderInfraGateway) {
        this.jsonUtil = jsonUtil;
        this.orderInfraGateway = orderInfraGateway;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-confirmed}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderConfirmed(String message) {
        log.info("Order confirmed receiver (String): {}", message);
        OrderMessageConsumer orderMessageConsumer = jsonUtil.fromJson(message, OrderMessageConsumer.class);
        log.info("Order confirmed receiver (Object): {}", orderMessageConsumer);
        orderInfraGateway.updateOrderStatus(orderMessageConsumer);
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-fail}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderFail(String message) {
        log.info("Order fail receiver (String): {}", message);
        OrderMessageConsumer orderMessageConsumer = jsonUtil.fromJson(message, OrderMessageConsumer.class);
        log.info("Order fail receiver (Object): {}", orderMessageConsumer);
        orderInfraGateway.updateOrderStatus(orderMessageConsumer);
    }
}
