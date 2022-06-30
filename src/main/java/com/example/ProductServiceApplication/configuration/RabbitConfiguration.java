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
    private String DIRECT_XCHANGE_NAME;

    @Value("${routing-keys.product-service}")
    private String routingKeyProductService;

    @Value("${queue-names.components}")
    private String componentsQueueName;


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_XCHANGE_NAME);
    }

    @Bean
    public Queue componentsQueue() {
        return new Queue(componentsQueueName);
    }

    @Bean
    public Binding componentsBinding(DirectExchange directExchange, Queue componentsQueue) {
        return BindingBuilder.bind(componentsQueue).to(directExchange).with(routingKeyProductService);
    }

    @Bean
    public RabbitController rabbitController() {
        return new RabbitController();
    }

}
