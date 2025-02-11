package com.teste.pdf_service.infra.kafka.producer;

import com.teste.pdf_service.infra.kafka.OrderMessageConsumer;
import com.teste.pdf_service.infra.kafka.OrderWithProductsMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PdfProducer {

    private static final Logger log = LoggerFactory.getLogger(PdfProducer.class);

    @Value("${spring.kafka.topic.order-pdf}")
    private String orderPdfTopic;

    private final KafkaTemplate<String, OrderMessageConsumer> kafkaTemplate;
    private final KafkaTemplate<String, OrderWithProductsMessageProducer> kafkaTemplateOrderWithProducts;

    public PdfProducer(KafkaTemplate<String, OrderMessageConsumer> kafkaTemplate,
                       KafkaTemplate<String, OrderWithProductsMessageProducer> kafkaTemplateOrderWithProducts) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateOrderWithProducts = kafkaTemplateOrderWithProducts;
    }

    public void sendOrderPdfTopicCreated(OrderMessageConsumer message) {
        try {
            log.info("Sending message to Topic Created: Order-Pdf");
            kafkaTemplate.send(orderPdfTopic, String.format("%s-%s", message.getId(), message.getStatus()), message);
        } catch (Exception e) {
            log.error("Error to send message to Topic Created: Order-Pdf", e);
            throw new RuntimeException("Error to send message to Topic: Order-Pdf", e);
        }
    }

    public void sendOrderPdfTopicConfirmedAndFail(OrderWithProductsMessageProducer orderMessageConsumer) {
        try {
            log.info("Sending message to Topic Confirmed|Fail: Order-Pdf");
            kafkaTemplateOrderWithProducts.send(
                    orderPdfTopic,
                    String.format("%s-%s", orderMessageConsumer.id(), orderMessageConsumer.status()),
                    orderMessageConsumer);
        } catch (Exception e) {
            log.error("Error to send message to Topic Confirmed|Fail: Order-Pdf", e);
            throw new RuntimeException("Error to send message to Topic: Order-Pdf", e);
        }
    }
}
