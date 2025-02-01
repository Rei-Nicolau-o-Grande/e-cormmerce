package com.teste.product_service.infra.mapper;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.infra.persistence.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityMapper {

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(
                product.id(),
                product.name(),
                product.description(),
                product.price(),
                product.code(),
                product.stock(),
                product.createdAt(),
                product.updatedAt(),
                product.deactivatedAt(),
                product.active()
        );
    }

    public static Product toDomain(ProductEntity productEntity) {
        return new Product(
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
        );
    }
}
