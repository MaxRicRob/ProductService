package com.example.ProductServiceApplication.api;

import com.example.ProductServiceApplication.service.ProductService;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;


public class RabbitController {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.product-service}")
    public String getAllProductComponents(Message message) {

        var input = new String(message.getBody(), StandardCharsets.UTF_8);

        if (input.equals("getComponents")) {
            return new Gson().toJson(productService.getAllProductComponents());
        }
        if (input.equals("getDefaultProducts")) {
            return new Gson().toJson(productService.getAllDefaultProducts());
        }
        return null;


    }


    @RabbitListener(queues = "${queue-names.user-products}")
    public String getUserProducts(Message message) {

        return new Gson().toJson(productService.getAllProductsFromUser(message.toString()));
    }





}
