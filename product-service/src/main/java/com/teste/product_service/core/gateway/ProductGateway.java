package com.teste.product_service.core.gateway;

import com.teste.product_service.core.domain.entities.Product;

public interface ProductGateway {

    Product createProduct(Product product);
    Product getProductById(String id);
    Product updateProduct(String id, Product product);
    Product deactiveProduct(String id);
}
