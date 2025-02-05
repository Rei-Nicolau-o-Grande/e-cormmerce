package com.teste.product_service.core.gateway;

import com.teste.product_service.core.domain.entities.Product;

import java.util.List;

public interface ProductGateway {

    Product createProduct(Product product);
    Product findProductById(String id);
    Product updateProduct(String id, Product product);
    Product deactiveProduct(String id);
    List<Product> findAllProductsActiveTrueAndStockGreaterThanZero();
}
