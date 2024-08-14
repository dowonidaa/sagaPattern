package com.market.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    @Value("${message.queue.payment}")
    private String queuePayment;

    @Value("${message.queue.err.order}")
    private String queueErrOrder;

    private final RabbitTemplate rabbitTemplate;

    public void reduceProductAmount(DeliveryMessage deliveryMessage) {
        Integer productId = deliveryMessage.getProductId();
        Integer productQuantity = deliveryMessage.getProductQuantity();

        if (productId != 1 || productQuantity > 1) {
            this.rollbackProduct(deliveryMessage);
            return;
        }
        rabbitTemplate.convertAndSend(queuePayment, deliveryMessage);
    }

    public void rollbackProduct(DeliveryMessage deliveryMessage) {
        log.info("PRODUCT ROLLBACK");
        if(!StringUtils.hasText(deliveryMessage.getErrorType()))
            deliveryMessage.setErrorType("PRODUCT ERROR");
        rabbitTemplate.convertAndSend(queueErrOrder, deliveryMessage);
    }
}
