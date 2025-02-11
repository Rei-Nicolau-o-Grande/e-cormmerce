package com.teste.pdf_service.infra.kafka.consumer;

import com.teste.pdf_service.infra.kafka.OrderMessageConsumer;
import com.teste.pdf_service.infra.kafka.OrderWithProductsMessageProducer;
import com.teste.pdf_service.infra.utils.JsonUtil;
import com.teste.pdf_service.infra.utils.PdfService;
import com.teste.pdf_service.infra.utils.PdfWithProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    private final JsonUtil jsonUtil;
    private final PdfService pdfService;
    private final PdfWithProductsService pdfWithProductsService;

    public OrderConsumer(JsonUtil jsonUtil,
                         PdfService pdfService,
                         PdfWithProductsService pdfWithProductsService) {
        this.jsonUtil = jsonUtil;
        this.pdfService = pdfService;
        this.pdfWithProductsService = pdfWithProductsService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderCreated(String message) {
        OrderMessageConsumer orderMessageConsumer = jsonUtil.fromJson(message, OrderMessageConsumer.class);
        pdfService.generatePdf(orderMessageConsumer);
        log.info("Order-created Pdf: {}", orderMessageConsumer);
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-fail}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderFail(String message) {
        OrderWithProductsMessageProducer orderWithProductsMessageProducer = jsonUtil.fromJson(message, OrderWithProductsMessageProducer.class);
        pdfWithProductsService.generatePdf(orderWithProductsMessageProducer);
        log.info("Order-fail Pdf: {}", orderWithProductsMessageProducer);
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.order-confirmed}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderConfirmed(String message) {
        OrderWithProductsMessageProducer orderWithProductsMessageProducer = jsonUtil.fromJson(message, OrderWithProductsMessageProducer.class);
        pdfWithProductsService.generatePdf(orderWithProductsMessageProducer);
        log.info("Order-confirmed Pdf: {}", orderWithProductsMessageProducer);
    }


}
