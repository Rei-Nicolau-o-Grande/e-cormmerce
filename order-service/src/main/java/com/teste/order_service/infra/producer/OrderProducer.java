package com.teste.order_service.infra.producer;

import com.teste.order_service.infra.dtos.OrderResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    @Value("${spring.kafka.topic.order-created}")
    private String orderCreatedTopic;

    private final KafkaTemplate<String, OrderResponseDto> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderResponseDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderTopicCreated(OrderResponseDto orderResponseDto) {
        try {
            kafkaTemplate.send(orderCreatedTopic, orderResponseDto.id(), orderResponseDto);
        } catch (Exception e) {
            throw new RuntimeException("Error to send message to Kafka", e);
        }
    }
}
