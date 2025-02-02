package com.teste.product_service.infra.config;

import com.teste.product_service.core.gateway.ProductGateway;
import com.teste.product_service.core.usecase.CreateProductUseCase;
import com.teste.product_service.core.usecase.CreateProductUseCaseImpl;
import com.teste.product_service.core.usecase.FindProductByIdUseCase;
import com.teste.product_service.core.usecase.FindProductByIdUseCaseImpl;
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
}
