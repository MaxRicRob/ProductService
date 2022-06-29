package com.example.ProductServiceApplication.api;

import com.example.ProductServiceApplication.domain.ProductComponent;
import com.example.ProductServiceApplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class RabbitController {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.components}")
    public List<ProductComponent> getAllProductComponents() {
        return productService.getAllProductComponents();

    }


}
