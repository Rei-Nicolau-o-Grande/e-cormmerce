package com.teste.product_service.infra.config;

import com.teste.product_service.core.gateway.ProductGateway;
import com.teste.product_service.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateProductUseCase createProductUseCase(ProductGateway productGateway) {
        return new CreateProductUseCaseImpl(productGateway);
    }

    @Bean
    public FindProductByIdUseCase findProductByIdUseCase(ProductGateway productGateway) {
        return new FindProductByIdUseCaseImpl(productGateway);
    }

    @Bean
    public DeactiveProductUseCase deactiveProductUseCase(ProductGateway productGateway) {
        return new DeactiveProductUseCaseImpl(productGateway);
    }
}
