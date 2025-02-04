package com.teste.product_service.infra.kafka.consumer;

import com.teste.product_service.infra.gateway.ProductInfraGateway;
import com.teste.product_service.infra.kafka.OrderMessageConsumer;
import com.teste.product_service.infra.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductConsumer.class);

    private final JsonUtil jsonUtil;
    private final ProductInfraGateway productInfraGateway;

    public ProductConsumer(JsonUtil jsonUtil,
                           ProductInfraGateway productInfraGateway) {
        this.jsonUtil = jsonUtil;
        this.productInfraGateway = productInfraGateway;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderCreated(String message) {
        log.info("Order created receiver (String): {}", message);

        OrderMessageConsumer orderMessageConsumer = jsonUtil.fromJson(message, OrderMessageConsumer.class);

        log.info("Order created receiver (Object): {}", orderMessageConsumer);

        productInfraGateway.sendOrderTopicConfirmedOrFail(orderMessageConsumer);
    }
}
