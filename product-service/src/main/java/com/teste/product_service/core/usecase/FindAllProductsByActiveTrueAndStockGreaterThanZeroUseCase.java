package com.teste.product_service.core.usecase;

import com.teste.product_service.core.domain.entities.Product;

import java.util.List;

public interface FindAllProductsByActiveTrueAndStockGreaterThanZeroUseCase {
    List<Product> execute();
}
