package com.teste.order_service.infra.controllers;

import com.teste.order_service.core.domain.entities.Order;
import com.teste.order_service.core.usecase.CreateOrderUseCase;
import com.teste.order_service.infra.dtos.OrderRequestDto;
import com.teste.order_service.infra.dtos.OrderResponseDto;
import com.teste.order_service.infra.mapper.OrderDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestDto) {
        Order order = createOrderUseCase.execute(OrderDtoMapper.toDomain(requestDto));
        URI location = URI.create(String.format("/api/v1/order/%s", order.id()));
        return ResponseEntity.created(location).body(OrderDtoMapper.toDtoResponse(order));
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testOrderService() {
        Map<String, String> response = Map.of("message", "Order service is working.");
        return ResponseEntity.ok(response);
    }
}
