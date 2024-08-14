package com.market.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    @Value("${message.queue.product}")
    private String queueProduct;

    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderEndPoint.OrderRequestDto orderRequestDto) {
        Order order = orderRepository.save(orderRequestDto.toOrder());
        DeliveryMessage deliveryMessage = orderRequestDto.toDeliveryMessage(order.getOrderId());
        rabbitTemplate.convertAndSend(queueProduct, deliveryMessage);
        return order;

    }

    @Transactional(readOnly = true)
    public Order getOrder(Long orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Transactional
    public void rollbackOrder(DeliveryMessage deliveryMessage) {
        Order order = orderRepository.findByOrderId(deliveryMessage.getOrderId());
        order.cancelOrder(deliveryMessage.getErrorType());
    }
}
