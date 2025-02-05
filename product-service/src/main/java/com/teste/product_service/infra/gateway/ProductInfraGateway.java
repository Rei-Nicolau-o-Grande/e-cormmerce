package com.teste.product_service.infra.gateway;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;
import com.teste.product_service.infra.exceptions.EntityNotFoundException;
import com.teste.product_service.infra.exceptions.ProductIsAlreadyDeactivatedException;
import com.teste.product_service.infra.kafka.OrderMessageConsumer;
import com.teste.product_service.infra.kafka.StatusOrder;
import com.teste.product_service.infra.kafka.producer.ProductProducer;
import com.teste.product_service.infra.mapper.ProductEntityMapper;
import com.teste.product_service.infra.persistence.ProductEntity;
import com.teste.product_service.infra.persistence.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ProductInfraGateway implements ProductGateway {

    private static final Logger log = LoggerFactory.getLogger(ProductInfraGateway.class);

    private final ProductRepository productRepository;
    private final ProductProducer productProducer;

    public ProductInfraGateway(ProductRepository productRepository,
                               ProductProducer productProducer) {
        this.productRepository = productRepository;
        this.productProducer = productProducer;
    }

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productRepository.save(ProductEntityMapper.toEntity(product));
        log.info("Product created");
        return ProductEntityMapper.toDomain(productEntity);
    }

    @Override
    public Product findProductById(String id) {
        log.info("Finding product by id: {}", id);
        return productRepository.findById(id)
                .map(ProductEntityMapper::toDomain)
                .orElseThrow(
                        () -> new EntityNotFoundException("Product not found")
                );
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return null;
    }

    @Override
    public Product deactiveProduct(String id) {
        return productRepository.findById(id)
                .map(productEntity -> {
                    if (!productEntity.getActive()) {
                        throw new ProductIsAlreadyDeactivatedException("Product is already deactivated");
                    }
                    productEntity.setDeactivatedAt(LocalDateTime.now());
                    productEntity.setActive(false);
                    log.info("Product deactivated");
                    return ProductEntityMapper.toDomain(productRepository.save(productEntity));
                })
                .orElseThrow(
                        () -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public List<Product> findAllProductsActiveTrueAndStockGreaterThanZero() {
        List<Product> products = productRepository.findAllByActiveTrueAndStockGreaterThan(0).stream()
                .map(ProductEntityMapper::toDomain)
                .toList();

        log.info("Finding all products");
        return products;
    }

    public void sendOrderTopicConfirmedOrFail(OrderMessageConsumer orderMessageConsumer) {
        boolean hasUnavailableProduct = orderMessageConsumer.getProductsId().stream()
                .map(productRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(product -> !product.getActive() || product.getStock() <= 0);

        if (hasUnavailableProduct) {
            orderMessageConsumer.setStatus(StatusOrder.FAIL);
            productProducer.sendOrderTopicFail(orderMessageConsumer);
            log.info("Order sent to Topic: Order-Fail");
        } else {
            orderMessageConsumer.getProductsId().forEach(productId ->
                    productRepository.findById(productId).ifPresent(product -> {
                        product.setStock(product.getStock() - 1);
                        productRepository.save(product);
                    })
            );
            orderMessageConsumer.setStatus(StatusOrder.CONFIRMED);
            productProducer.sendOrderTopicConfirmed(orderMessageConsumer);
            log.info("Order sent to Topic: Order-Confirmed");
        }
    }
}
