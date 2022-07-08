package com.example.ProductServiceApplication.configuration;


import com.example.ProductServiceApplication.api.RabbitController;
import com.example.ProductServiceApplication.domain.PriceService;
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
    @Value("${routing-keys.price-service}")
    private String priceServiceRoutingKey;
    @Value("${queue-names.product-service}")
    private String productServiceQueueName;
    @Value("${queue-names.price-service}")
    private String priceServiceQueueName;


    @Bean
    public RabbitController rabbitController() {
        return new RabbitController();
    }

    @Bean
    public PriceService priceService() {
        return new PriceService();
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
    public Queue priceServiceQueue() {
        return new Queue(priceServiceQueueName);
    }

    @Bean
    public Binding productServiceBinding(DirectExchange directExchange, Queue productServiceQueue) {
        return BindingBuilder.bind(productServiceQueue).to(directExchange).with(productServiceRoutingKey);
    }

    @Bean
    public Binding priceServiceBinding(DirectExchange directExchange, Queue priceServiceQueue) {
        return BindingBuilder.bind(priceServiceQueue).to(directExchange).with(priceServiceRoutingKey);
    }

}
