package com.teste.order_service.infra.config;

import com.teste.order_service.core.gateway.OrderGateway;
import com.teste.order_service.core.usecase.CreateOrderUseCase;
import com.teste.order_service.core.usecase.CreateOrderUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderGateway orderGateway) {
        return new CreateOrderUseCaseImpl(orderGateway);
    }
}
