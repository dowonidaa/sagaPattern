package com.market.product;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductApplicationQueueConfig {

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.product}")
    private String queueProduct;

    @Value("${message.queue.payment}")
    private String queuePayment;

    @Value("${message.err.exchange}")
    private String exchangeErr;

    @Value("${message.queue.err.product}")
    private String queueErrProduct;

    @Value("${message.queue.err.order}")
    private String queueErrOrder;

}
