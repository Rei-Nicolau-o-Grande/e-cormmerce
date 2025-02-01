package com.teste.product_service.infra.gateway;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;
import com.teste.product_service.infra.mapper.ProductEntityMapper;
import com.teste.product_service.infra.persistence.ProductEntity;
import com.teste.product_service.infra.persistence.ProductRepository;
import org.springframework.stereotype.Component;

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
    public Product getProductById(String id) {
        return null;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return null;
    }

    @Override
    public Product deactiveProduct(String id) {
        return null;
    }
}
