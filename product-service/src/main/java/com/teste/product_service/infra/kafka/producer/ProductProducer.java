package com.teste.product_service.infra.kafka.producer;

import com.teste.product_service.infra.kafka.OrderMessageConsumer;
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

    private final KafkaTemplate<String, OrderMessageConsumer> kafkaTemplate;

    public ProductProducer(KafkaTemplate<String, OrderMessageConsumer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderTopicConfirmed(OrderMessageConsumer orderMessageConsumer) {
        try {
            log.info("Sending message to Topic: Order-Confirmed");
            kafkaTemplate.send(orderConfirmedTopic, orderMessageConsumer.getId(), orderMessageConsumer);
        } catch (Exception e) {
            log.error("Error to send message to Topic: Order-Confirmed", e);
            throw new RuntimeException("Error to send message to Topic: Order-Confirmed", e);
        }
    }

    public void sendOrderTopicFail(OrderMessageConsumer orderMessageConsumer) {
        try {
            log.info("Sending message to Topic: Order-Fail");
            kafkaTemplate.send(orderFailTopic, orderMessageConsumer.getId(), orderMessageConsumer);
        } catch (Exception e) {
            log.error("Error to send message to Topic: Order-Fail", e);
            throw new RuntimeException("Error to send message to Topic: Order-Fail", e);
        }
    }
}
