package com.market.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    @Value("${message.queue.product}")
    private String queueProduct;

    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;

    public Order createOrder(OrderEndPoint.OrderRequestDto orderRequestDto) {
        Order order = orderRepository.createOrder(orderRequestDto);
        DeliveryMessage deliveryMessage = orderRequestDto.toDeliveryMessage(order.getOrderId());
        rabbitTemplate.convertAndSend(queueProduct, deliveryMessage);
        return order;

    }

    public Order getOrder(UUID orderId) {
        return orderRepository.getOrder(orderId);
    }

    public void rollbackOrder(DeliveryMessage deliveryMessage) {
        Order order = orderRepository.getOrder(deliveryMessage.getOrderId());
        order.cancelOrder(deliveryMessage.getErrorType());
    }
}
