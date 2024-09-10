package com.market.order;

import java.util.HashMap;
import java.util.Map;

//@Repository
public class OrderMemoryRepository implements OrderRepository {

    private Map<Long, Order> orderStore = new HashMap<>();


    @Override
    public Order findByOrderId(Long orderId) {
        return orderStore.get(orderId);
    }

    @Override
    public Order save(Order order) {
        return orderStore.put(order.getOrderId(), order);
    }
}
