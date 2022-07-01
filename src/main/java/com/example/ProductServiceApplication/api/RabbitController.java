package com.example.ProductServiceApplication.api;

import com.example.ProductServiceApplication.entity.Product;
import com.example.ProductServiceApplication.domain.ProductService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class RabbitController {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${queue-names.product-service}")
    public String handleRequest(Message message) {

        var input = parseMessage(message);

        switch (input[0]) {
            case "getComponents": {
                return new Gson().toJson(productService.getAllProductComponents());
            }
            case "getDefaultProducts": {
                return new Gson().toJson(productService.getAllDefaultProducts());
            }
            case "getProductsFromUser": {
                var userName = input[1];
                return new Gson().toJson(productService.getAllProductsFromUser(userName));
            }
            case "deleteProduct": {
                var uuid = UUID.fromString(input[1]);
                productService.deleteProduct(uuid);
                return new Gson().toJson(new Product());
            }
            case "createProduct": {
                var product = new Gson().fromJson(input[1], Product.class);
                productService.createProduct(product);
                return new Gson().toJson(product);
            }
            case "updateProduct": {
                var product = new Gson().fromJson(input[1], Product.class);
                productService.updateProduct(product);
                return new Gson().toJson(product);
            }
            default: {
                log.info("invalid input message - unable to parse");
                return new Gson().toJson(new Product());

            }
        }
    }

    private String[] parseMessage(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8).split("_");
    }


}
