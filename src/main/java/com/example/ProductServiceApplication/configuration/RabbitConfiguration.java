package com.example.ProductServiceApplication.configuration;


import com.example.ProductServiceApplication.api.RabbitController;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    @Value("${xchange.name}")
    private String directXchangeName;

    @Value("${routing-keys.product-service}")
    private String productServiceRoutingKey;

    @Value("${queue-names.product-service}")
    private String productServiceQueueName;

    @Value("${routing-keys.user-products}")
    private String userProductsRoutingKey;

    @Value("${queue-names.user-products}")
    private String userProductsQueueName;

    @Bean
    public RabbitController rabbitController() {
        return new RabbitController();
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }

    @Bean
    public Queue productServiceQueue() {
        return new Queue(productServiceQueueName);
    }

    @Bean
    public Binding productServiceBinding(DirectExchange directExchange, Queue productServiceQueue) {
        return BindingBuilder.bind(productServiceQueue).to(directExchange).with(productServiceRoutingKey);
    }

    @Bean
    public Queue userProductsQueue() {
        return new Queue(userProductsQueueName);
    }

    @Bean
    public Binding userProductsBinding(DirectExchange directExchange, Queue userProductsQueue) {
        return BindingBuilder.bind(userProductsQueue).to(directExchange).with(userProductsRoutingKey);
    }


}
