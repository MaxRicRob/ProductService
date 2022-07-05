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

        var key = getKey(message);

        switch (key) {
            case "getComponents": {
                return getComponentsFromProductService();
            }
            case "getDefaultProducts": {
                return getDefaultProductsFromProductService();
            }
            case "getProductsFromUser": {
                var userName = getMessageBody(message);
                return getAllUserProductsFromProductService(userName);
            }
            case "deleteProduct": {
                var userId = extractUserIdFromMessage(message);
                return deleteProductById(userId);
            }
            case "createProduct": {
                var product = extractProductFromMessage(message);
                return createProduct(product);
            }
            case "updateProduct": {
                var product = extractProductFromMessage(message);
                return updateProduct(product);
            }
            default: {
                return logInvalidInput();
            }
        }
    }

    private UUID extractUserIdFromMessage(Message message) {
        return UUID.fromString(getMessageBody(message));
    }

    private Product extractProductFromMessage(Message message) {
        return new Gson().fromJson(getMessageBody(message), Product.class);
    }

    private String getMessageBody(Message message) {
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

    private String getAllUserProductsFromProductService(String userName) {
        return new Gson().toJson(productService.getAllProductsFromUser(userName));
    }

    private String getDefaultProductsFromProductService() {
        return new Gson().toJson(productService.getAllDefaultProducts());
    }

    private String getComponentsFromProductService() {
        return new Gson().toJson(productService.getAllProductComponents());
    }

    private String getKey(Message message) {
        return (String) message.getMessageProperties().getHeaders().get("key");
    }


}
