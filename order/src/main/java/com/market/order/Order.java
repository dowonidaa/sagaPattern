package com.market.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String userId;
    private String orderStatus; //enum 사용
    private String errorType;

    public void cancelOrder(String receiveErrorType) {
        orderStatus = "CANCELED";
        errorType = receiveErrorType;
    }



}
