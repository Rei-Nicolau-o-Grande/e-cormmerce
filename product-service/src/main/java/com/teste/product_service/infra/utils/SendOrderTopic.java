package com.teste.product_service.infra.utils;

import com.teste.product_service.infra.kafka.OrderMessageConsumer;
import com.teste.product_service.infra.kafka.StatusOrder;
import com.teste.product_service.infra.kafka.producer.ProductProducer;
import com.teste.product_service.infra.persistence.ProductEntity;
import com.teste.product_service.infra.persistence.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SendOrderTopic {

    private static final Logger log = LoggerFactory.getLogger(SendOrderTopic.class);

    private final ProductRepository productRepository;
    private final TransformerOrderProducer transformerOrderProducer;
    private final ProductProducer productProducer;

    public SendOrderTopic(ProductRepository productRepository, TransformerOrderProducer transformerOrderProducer, ProductProducer productProducer) {
        this.productRepository = productRepository;
        this.transformerOrderProducer = transformerOrderProducer;
        this.productProducer = productProducer;
    }

    public void sendOrderTopicConfirmedOrFail(OrderMessageConsumer orderMessageConsumer) {
        List<ProductEntity> products = orderMessageConsumer.getProductsId().stream()
                .map(productRepository::findById)
                .flatMap(Optional::stream) // Filtra apenas os existentes (removendo os Optionals vazios)
                .toList();

        if (products.size() != orderMessageConsumer.getProductsId().size() ||
                products.stream().anyMatch(p -> !p.getActive() || p.getStock() <= 0)) {
            orderMessageConsumer.setStatus(StatusOrder.FAIL);
            productProducer.sendOrderTopicFail(transformerOrderProducer.transform(orderMessageConsumer));
            log.info("Order sent to Topic: Order-Fail");
            return;
        }

        // Atualiza estoque e confirma pedido
        products.forEach(product -> {
            product.setStock(product.getStock() - 1);
            productRepository.save(product);
        });

        orderMessageConsumer.setStatus(StatusOrder.CONFIRMED);
        productProducer.sendOrderTopicConfirmed(transformerOrderProducer.transform(orderMessageConsumer));
        log.info("Order sent to Topic: Order-Confirmed");
    }


}
