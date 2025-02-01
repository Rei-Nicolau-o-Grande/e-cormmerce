package com.teste.product_service.core.usecase;

import com.teste.product_service.core.domain.entities.Product;

public interface FindProductByIdUseCase {
    Product execute(String id);
}
