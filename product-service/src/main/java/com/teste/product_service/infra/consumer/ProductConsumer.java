package com.teste.product_service.infra.consumer;

import com.teste.product_service.infra.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductConsumer.class);

    private final JsonUtil jsonUtil;

    public ProductConsumer(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderCreated(String message) {
        log.info("Order created receiver (String): {}", message);

        OrderResponseConsumer orderResponseConsumer = jsonUtil.fromJson(message, OrderResponseConsumer.class);

        log.info("Order created receiver (Object): {}", orderResponseConsumer);
    }
}
