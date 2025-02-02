package com.teste.product_service.core.usecase;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;

public class DeactiveProductUseCaseImpl implements DeactiveProductUseCase {

    private final ProductGateway productGateway;

    public DeactiveProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Product execute(String id) {
        return productGateway.deactiveProduct(id);
    }
}
