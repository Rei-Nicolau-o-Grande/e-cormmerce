package com.teste.product_service.infra.mapper;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.infra.dtos.ProductRequestDto;
import com.teste.product_service.infra.dtos.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {

    public static ProductResponseDto toDtoResponse(Product product) {
        return new ProductResponseDto(
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

    public static Product toDomain(ProductRequestDto productRequestDto) {
        return new Product(
                null,
                productRequestDto.name(),
                productRequestDto.description(),
                productRequestDto.price(),
                null,
                productRequestDto.stock(),
                null,
                null,
                null,
                null
        );
    }
}
