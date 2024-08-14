package com.market.order;

import java.util.UUID;

public interface OrderRepository {

    Order getOrder(UUID orderId);

    Order createOrder(OrderEndPoint.OrderRequestDto orderRequestDto);
}
