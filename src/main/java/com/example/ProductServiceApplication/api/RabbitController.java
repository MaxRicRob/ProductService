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

        var key = getKeyFrom(message);

        switch (key) {
            case "getComponents": {
                return getComponents();
            }
            case "getDefaultProducts": {
                return getDefaultProducts();
            }
            case "getProductsFromUser": {
                var userName = getBodyFrom(message);
                return getAllUserProducts(userName);
            }
            case "deleteProduct": {
                var userId = extractUserIdFrom(message);
                return deleteProductById(userId);
            }
            case "createProduct": {
                var product = extractProductFrom(message);
                return createProduct(product);
            }
            case "updateProduct": {
                var product = extractProductFrom(message);
                return updateProduct(product);
            }
            default: {
                return logInvalidInput();
            }
        }
    }

    private UUID extractUserIdFrom(Message message) {
        return UUID.fromString(getBodyFrom(message));
    }

    private Product extractProductFrom(Message message) {
        return new Gson().fromJson(getBodyFrom(message), Product.class);
    }

    private String getBodyFrom(Message message) {
        return new String(message.getBody(), StandardCharsets.UTF_8);
    }

    private String logInvalidInput() {
        log.info("invalid input message - unable to parse");
        return new Gson().toJson(new Product());
    }

    private String updateProduct(Product product) {
        productService.updateProduct(product);
        return new Gson().toJson(product);
    }

    private String createProduct(Product product) {
        productService.createProduct(product);
        return new Gson().toJson(product);
    }

    private String deleteProductById(UUID uuid) {
        productService.deleteProduct(uuid);
        return new Gson().toJson(new Product());
    }

    private String getAllUserProducts(String userName) {
        return new Gson().toJson(productService.getAllProductsFromUser(userName));
    }

    private String getDefaultProducts() {
        return new Gson().toJson(productService.getAllDefaultProducts());
    }

    private String getComponents() {
        return new Gson().toJson(productService.getAllProductComponents());
    }

    private String getKeyFrom(Message message) {
        return (String) message.getMessageProperties().getHeaders().get("key");
    }


}
