package com.teste.product_service.infra.exceptions;

public class ProductIsAlreadyDeactivatedException extends RuntimeException {
    public ProductIsAlreadyDeactivatedException(String message) {
        super(message);
    }
}
