package com.market.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    @Value("${message.queue.payment}")
    private String queuePayment;

    @Value("${message.queue.err.order}")
    private String queueErrOrder;

    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void reduceProductAmount(DeliveryMessage deliveryMessage) {
        Long productId = deliveryMessage.getProductId();
        Integer productQuantity = deliveryMessage.getProductQuantity();

        if (productId ==  null || productQuantity > 1) {
            this.rollbackProduct(deliveryMessage);
            return;
        }
        Product product = productRepository.findByProductId(productId);
        product.reduce(productQuantity);
        rabbitTemplate.convertAndSend(queuePayment, deliveryMessage);
    }

    @Transactional
    public void rollbackProduct(DeliveryMessage deliveryMessage) {
        log.info("PRODUCT ROLLBACK");
        if(!StringUtils.hasText(deliveryMessage.getErrorType()))
            deliveryMessage.setErrorType("PRODUCT ERROR");
        Product productId = productRepository.findByProductId(deliveryMessage.getProductId());
        productId.rollbackQuantity(deliveryMessage.getProductQuantity());
        rabbitTemplate.convertAndSend(queueErrOrder, deliveryMessage);
    }
}
