package com.market.order;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class OrderMemoryRepository implements OrderRepository {

    private Map<UUID, Order> orderStore = new HashMap<>();

    @Override
    public Order getOrder(UUID orderId) {
        return orderStore.get(orderId);

    }

    @Override
    public Order createOrder(OrderEndPoint.OrderRequestDto orderRequestDto) {
        Order order = orderRequestDto.toOrder();
        orderStore.put(order.getOrderId(), order);
        return order;
    }
}
