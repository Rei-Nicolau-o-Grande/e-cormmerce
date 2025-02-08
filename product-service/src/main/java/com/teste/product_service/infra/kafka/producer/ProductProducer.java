package com.teste.product_service.infra.kafka.producer;

import com.teste.product_service.infra.kafka.OrderWithProductsMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {

    private static final Logger log = LoggerFactory.getLogger(ProductProducer.class);

    @Value("${spring.kafka.topic.order-confirmed}")
    private String orderConfirmedTopic;

    @Value("${spring.kafka.topic.order-fail}")
    private String orderFailTopic;

    private final KafkaTemplate<String, OrderWithProductsMessageProducer> kafkaTemplate;

    public ProductProducer(KafkaTemplate<String, OrderWithProductsMessageProducer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderTopicConfirmed(OrderWithProductsMessageProducer OrderWithProductsMessageProducer) {
        try {
            log.info("Sending message to Topic: Order-Confirmed");
            kafkaTemplate.send(orderConfirmedTopic, OrderWithProductsMessageProducer.id(), OrderWithProductsMessageProducer);
        } catch (Exception e) {
            log.error("Error to send message to Topic: Order-Confirmed", e);
            throw new RuntimeException("Error to send message to Topic: Order-Confirmed", e);
        }
    }

    public void sendOrderTopicFail(OrderWithProductsMessageProducer OrderWithProductsMessageProducer) {
        try {
            log.info("Sending message to Topic: Order-Fail");
            kafkaTemplate.send(orderFailTopic, OrderWithProductsMessageProducer.id(), OrderWithProductsMessageProducer);
        } catch (Exception e) {
            log.error("Error to send message to Topic: Order-Fail", e);
            throw new RuntimeException("Error to send message to Topic: Order-Fail", e);
        }
    }
}
