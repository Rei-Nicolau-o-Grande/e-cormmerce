package com.teste.order_service.core.gateway;

import com.teste.order_service.core.domain.entities.Order;

public interface OrderGateway {

    Order createOrder(Order order);
}
