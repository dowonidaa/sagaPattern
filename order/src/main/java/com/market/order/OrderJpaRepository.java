package com.market.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> ,OrderRepository{
    @Override
    Order findByOrderId(Long orderId);

}
