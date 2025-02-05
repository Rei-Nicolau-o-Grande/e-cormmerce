package com.teste.product_service.core.usecase;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;

import java.util.List;

public class FindAllProductsByActiveTrueAndStockGreaterThanZeroUseCaseImpl implements FindAllProductsByActiveTrueAndStockGreaterThanZeroUseCase {

    private final ProductGateway productGateway;

    public FindAllProductsByActiveTrueAndStockGreaterThanZeroUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public List<Product> execute() {
        return productGateway.findAllProductsActiveTrueAndStockGreaterThanZero();
    }
}
