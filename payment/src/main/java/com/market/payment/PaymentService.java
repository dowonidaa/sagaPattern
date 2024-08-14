package com.market.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentRepository paymentRepository;

    @Value("${message.queue.err.product}")
    private String queueErrProduct;

    public void createPayment(DeliveryMessage deliveryMessage) {
        Payment payment = toEntity(deliveryMessage);
        Integer payAmount = payment.getPayAmount();
        if (payAmount > 10000) {
            log.error("Payment amount exceeds limit: {}", payAmount);
            deliveryMessage.setErrorType("PAYMENT_LIMIT_EXCEEDED");
            this.rollbackPayment(deliveryMessage);
            return;
        }
        paymentRepository.save(payment);
    }

    private Payment toEntity(DeliveryMessage deliveryMessage) {
        return Payment.builder()
                .userId(deliveryMessage.getUserId())
                .payAmount(deliveryMessage.getPayAmount())
                .payStatus("success")
                .build();
    }

    public void rollbackPayment(DeliveryMessage deliveryMessage) {
        log.info("PAYMENT ROLLBACK !!");
        rabbitTemplate.convertAndSend(queueErrProduct, deliveryMessage);
    }
}
