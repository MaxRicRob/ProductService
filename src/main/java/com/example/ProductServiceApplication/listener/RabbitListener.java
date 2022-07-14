package com.example.ProductServiceApplication.listener;

import com.example.ProductServiceApplication.domain.MessageType;
import com.example.ProductServiceApplication.domain.ProductService;
import com.example.ProductServiceApplication.domain.entity.Product;
import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Slf4j
public class RabbitListener {

    @Autowired
    private ProductService productService;

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${queue-names.product-service}")
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
                    log.info("get components request processed");
                    return getComponents();
                }
                case GET_DEFAULT_PRODUCTS: {
                    log.info("get default product request processed");
                    return getDefaultProducts();
                }
                case GET_PRODUCTS_FROM_USER: {
                    var userName = getBodyFrom(message);
                    log.info("get products from user {} request processed", userName);
                    return getAllUserProducts(userName);
                }
                case DELETE_PRODUCT: {
                    var userId = extractUserIdFrom(message);
                    log.info("delete products with id {} request processed", userId);
                    return deleteProductById(userId);
                }
                case CREATE_PRODUCT: {
                    var product = extractProductFrom(message);
                    log.info("create product for product {} request processed", product.getName());
                    return createProduct(product);
                }
                case UPDATE_PRODUCT: {
                    var product = extractProductFrom(message);
                    log.info("update product for product {} request processed", product.getName());
                    return updateProduct(product);
                }
                default: {
                    return errorResponse();
                }
            }
        } catch (ErrorResponseException e) {
            return errorResponse();
        }
    }

    private String errorResponse() {
        log.error("respond with message 'errorResponse'");
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
        return errorResponse();
    }

    private String updateProduct(Product product) throws ErrorResponseException {
        productService.updateProduct(product);
        return new Gson().toJson(product);
    }

    private String createProduct(Product product) throws ErrorResponseException {
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

