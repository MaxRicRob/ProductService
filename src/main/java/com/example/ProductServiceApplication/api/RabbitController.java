package com.example.ProductServiceApplication.api;

import com.example.ProductServiceApplication.api.error.ErrorResponseException;
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

        final MessageType messageType;
        try {
            messageType = MessageType.valueOf(message.getMessageProperties().getType());
        } catch (IllegalArgumentException e) {
            return logInvalidMessageType(message.getMessageProperties().getType());
        }

        try {
            switch (messageType) {
                case GET_COMPONENTS: {
                    return getComponents();
                }
                case GET_DEFAULT_PRODUCTS: {
                    return getDefaultProducts();
                }
                case GET_PRODUCTS_FROM_USER: {
                    var userName = getBodyFrom(message);
                    return getAllUserProducts(userName);
                }
                case DELETE_PRODUCT: {
                    var userId = extractUserIdFrom(message);
                    return deleteProductById(userId);
                }
                case CREATE_PRODUCT: {
                    var product = extractProductFrom(message);
                    return createProduct(product);
                }
                case UPDATE_PRODUCT: {
                    var product = extractProductFrom(message);
                    return updateProduct(product);
                }
                default: {
                    return logInvalidMessageType(message.getMessageProperties().getType());
                }
            }
        } catch (ErrorResponseException e) {
            return errorResponse();
        }
    }

    private String errorResponse() {
        return "errorResponse";
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

    private String logInvalidMessageType(String type) {
        log.info("invalid message type: " + type);
        return new Gson().toJson(new Product());
    }

    private String updateProduct(Product product) throws ErrorResponseException {
        productService.updateProduct(product);
        return new Gson().toJson(product);
    }

    private String createProduct(Product product) throws ErrorResponseException {
        product.setId(UUID.randomUUID());
        return new Gson().toJson(productService.createProduct(product));
    }

    private String deleteProductById(UUID uuid) throws ErrorResponseException {
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
}

