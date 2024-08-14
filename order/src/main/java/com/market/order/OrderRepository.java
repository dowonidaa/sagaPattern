package com.market.order;

import java.util.UUID;

public interface OrderRepository {

    Order findByOrderId(Long orderId);

    Order save(Order order);
}
