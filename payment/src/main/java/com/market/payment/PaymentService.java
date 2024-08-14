package com.market.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public void createPayment(DeliveryMessage deliveryMessage) {
        Payment payment = Payment.builder()
                    .paymentId(UUID.randomUUID())
                    .userId(deliveryMessage.getUserId())
                    .payAmount(deliveryMessage.getPayAmount())
                    .payStatus("success")
                .build();

        Integer payAmount = payment.getPayAmount();
        if (payAmount > 10000) {
            log.error("Payment amount exceeds limit: {}", payAmount);
        }
    }
}
