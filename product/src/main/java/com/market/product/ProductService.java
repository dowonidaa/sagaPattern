package com.market.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    @Value("${message.queue.payment}")
    private String queuePayment;

    private final RabbitTemplate rabbitTemplate;

    public void reduceProductAmount(DeliveryMessage deliveryMessage) {
        Integer productId = deliveryMessage.getProductId();
        Integer productQuantity = deliveryMessage.getProductQuantity();

        if (productId != 1 || productQuantity > 1) {
            return;
        }
        rabbitTemplate.convertAndSend(queuePayment, deliveryMessage);
    }
}
