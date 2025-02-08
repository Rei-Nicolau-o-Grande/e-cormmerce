package com.teste.product_service.infra.utils;

import com.teste.product_service.infra.kafka.OrderMessageConsumer;
import com.teste.product_service.infra.kafka.OrderWithProductsMessageProducer;
import com.teste.product_service.infra.kafka.ProductMessageProducer;
import com.teste.product_service.infra.persistence.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransformerOrderProducer {

    private final ProductRepository productRepository;

    public TransformerOrderProducer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public OrderWithProductsMessageProducer transform(OrderMessageConsumer orderMessageConsumer) {
        List<ProductMessageProducer> products = orderMessageConsumer.getProductsId().stream()
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(productEntity -> new ProductMessageProducer(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getDescription(),
                        productEntity.getPrice(),
                        productEntity.getCode(),
                        productEntity.getStock(),
                        productEntity.getCreatedAt(),
                        productEntity.getUpdatedAt(),
                        productEntity.getDeactivatedAt(),
                        productEntity.getActive()
                ))
                .toList();

        return new OrderWithProductsMessageProducer(
                orderMessageConsumer.getId(),
                products,
                orderMessageConsumer.getStatus(),
                orderMessageConsumer.getCreatedAt(),
                orderMessageConsumer.getUpdatedAt()
        );
    }
}
