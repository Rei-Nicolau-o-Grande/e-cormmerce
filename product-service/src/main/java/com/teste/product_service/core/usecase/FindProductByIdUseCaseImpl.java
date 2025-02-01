package com.teste.product_service.core.usecase;

import com.teste.product_service.core.domain.entities.Product;
import com.teste.product_service.core.gateway.ProductGateway;

public class FindProductByIdUseCaseImpl implements FindProductByIdUseCase {

    private final ProductGateway productGateway;

    public FindProductByIdUseCaseImpl(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public Product execute(String id) {
        return productGateway.findProductById(id);
    }
}
