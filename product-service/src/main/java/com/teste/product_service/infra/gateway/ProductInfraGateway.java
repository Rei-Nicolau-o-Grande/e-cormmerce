package com.teste.product_service.infra.gateway;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;
import com.teste.product_service.infra.exceptions.EntityNotFoundException;
import com.teste.product_service.infra.exceptions.ProductIsAlreadyDeactivatedException;
import com.teste.product_service.infra.mapper.ProductEntityMapper;
import com.teste.product_service.infra.persistence.ProductEntity;
import com.teste.product_service.infra.persistence.ProductRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductInfraGateway implements ProductGateway {

    private final ProductRepository productRepository;

    public ProductInfraGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productRepository.save(ProductEntityMapper.toEntity(product));
        return ProductEntityMapper.toDomain(productEntity);
    }

    @Override
    public Product findProductById(String id) {
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
                    return ProductEntityMapper.toDomain(productRepository.save(productEntity));
                })
                .orElseThrow(
                        () -> new EntityNotFoundException("Product not found"));
    }
}
