package com.market.order;

public interface OrderRepository {

    Order findByOrderId(Long orderId);

    Order save(Order order);
}
