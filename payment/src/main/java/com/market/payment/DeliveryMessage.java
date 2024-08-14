package com.market.payment;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DeliveryMessage {

    private Long orderId;
    private Long paymentId;

    private String userId;

    private Long productId;
    private Integer productQuantity;

    private Integer payAmount;

    private String errorType;
}
