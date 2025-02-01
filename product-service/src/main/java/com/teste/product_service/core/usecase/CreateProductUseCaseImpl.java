package com.teste.product_service.core.usecase;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;

public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductGateway productGateway;

    public CreateProductUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Product execute(Product product) {
        return productGateway.createProduct(product);
    }
}
