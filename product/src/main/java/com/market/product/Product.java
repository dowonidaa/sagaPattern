package com.market.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Integer productQuantity;

    public void reduce(Integer productQuantity) {
        this.productQuantity -= productQuantity;
    }

    public void rollbackQuantity(Integer productQuantity) {
        if (productQuantity > 1) {
            return;
        }
        this.productQuantity += productQuantity;
    }
}
