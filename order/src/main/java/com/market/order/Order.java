package com.market.order;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Builder
@ToString
@Data
public class Order {

    private UUID orderId;
    private String userId;
    private String orderStatus; //enum 사용
    private String errorType;

    public void cancelOrder(String receiveErrorType) {
        orderStatus = "CANCELED";
        errorType = receiveErrorType;
    }



}
