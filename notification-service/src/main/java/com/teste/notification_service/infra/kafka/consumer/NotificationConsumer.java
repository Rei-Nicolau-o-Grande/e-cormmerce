package com.teste.notification_service.infra.kafka.consumer;

import com.teste.notification_service.infra.kafka.OrderWithProductsMessageConsumer;
import com.teste.notification_service.infra.utils.EmailService;
import com.teste.notification_service.infra.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    String emailTeste = "teste@teste.com";

    private final JsonUtil jsonUtil;
    private final EmailService emailService;

    public NotificationConsumer(JsonUtil jsonUtil,
                                EmailService emailService) {
        this.jsonUtil = jsonUtil;
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-pdf}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTopicOrderPdf(String message) {
        OrderWithProductsMessageConsumer orderWithProductsMessageConsumer = jsonUtil.fromJson(message, OrderWithProductsMessageConsumer.class);
        log.info("(NOTIFICATION-SERVICE) Order pdf: {}", orderWithProductsMessageConsumer.id());
        try {
            emailService.sendEmail(emailTeste, orderWithProductsMessageConsumer);
            log.info("Email sent successfully status: {}", orderWithProductsMessageConsumer.status());
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }

//    @KafkaListener(
//            topics = "${spring.kafka.topic.order-confirmed}",
//            groupId = "${spring.kafka.consumer.group-id}"
//    )
//    public void consumeTopicOrderConfirmed(String message) {
//        OrderWithProductsMessageConsumer orderWithProductsMessageConsumer = jsonUtil.fromJson(message, OrderWithProductsMessageConsumer.class);
//        log.info("(NOTIFICATION-SERVICE) Order confirmed: {}", orderWithProductsMessageConsumer.id());
//        try {
//            emailService.sendEmail(emailTeste, orderWithProductsMessageConsumer);
//            log.info("Email sent successfully status: {}", orderWithProductsMessageConsumer.status());
//        } catch (Exception e) {
//            log.error("Error sending email: {}", e.getMessage());
//        }
//
//    }
//
//    @KafkaListener(
//            topics = "${spring.kafka.topic.order-fail}",
//            groupId = "${spring.kafka.consumer.group-id}"
//    )
//    public void consumeTopicOrderFail(String message) {
//        OrderWithProductsMessageConsumer orderWithProductsMessageConsumer = jsonUtil.fromJson(message, OrderWithProductsMessageConsumer.class);
//        log.info("(NOTIFICATION-SERVICE) Order fail: {}", orderWithProductsMessageConsumer.id());
//        try {
//            emailService.sendEmail(emailTeste, orderWithProductsMessageConsumer);
//            log.info("Email sent successfully status: {}", orderWithProductsMessageConsumer.status());
//        } catch (Exception e) {
//            log.error("Error sending email: {}", e.getMessage());
//        }
//    }
}
