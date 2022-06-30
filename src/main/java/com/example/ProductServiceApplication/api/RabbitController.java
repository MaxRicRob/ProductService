package com.example.ProductServiceApplication.api;

import com.example.ProductServiceApplication.service.ProductService;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


public class RabbitController {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.components}")
    public String getAllProductComponents() {

        return new Gson().toJson(productService.getAllProductComponents());
    }

    @RabbitListener(queues = "${queue-names.default-products}")
    public String getAllDefaultProducts() {


        return new Gson().toJson(productService.getAllDefaultProducts());
    }





}
