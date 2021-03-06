package com.example.ProductServiceApplication.listener;

import com.example.ProductServiceApplication.domain.ProductService;
import com.example.ProductServiceApplication.entity.Product;
import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

import static com.example.ProductServiceApplication.entity.MessageType.CREATE_PRODUCT;
import static com.example.ProductServiceApplication.entity.MessageType.DELETE_PRODUCT;
import static com.example.ProductServiceApplication.entity.MessageType.GET_COMPONENTS;
import static com.example.ProductServiceApplication.entity.MessageType.GET_DEFAULT_PRODUCTS;
import static com.example.ProductServiceApplication.entity.MessageType.GET_PRODUCTS_FROM_USER;
import static com.example.ProductServiceApplication.entity.MessageType.UPDATE_PRODUCT;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListenerTest {

    @InjectMocks
    private Listener listener;
    @Mock
    private ProductService productService;
    @Mock
    private Message message;
    @Mock
    private MessageProperties messageProperties;

    @BeforeEach
    void setUp() {
        when(message.getMessageProperties()).thenReturn(messageProperties);
    }

    @Test
    void handle_request_get_components() {
        when(messageProperties.getType()).thenReturn(GET_COMPONENTS.name());

        listener.handleRequest(message);

        verify(productService).getAllProductComponents();
    }

    @Test
    void handle_request_get_default_products() {
        when(messageProperties.getType()).thenReturn(GET_DEFAULT_PRODUCTS.name());

        listener.handleRequest(message);

        verify(productService).getAllDefaultProducts();
    }

    @Test
    void handle_request_get_products_from_user() {
        when(messageProperties.getType()).thenReturn(GET_PRODUCTS_FROM_USER.name());
        when(message.getBody()).thenReturn(new Gson().toJson("testUser").getBytes());

        listener.handleRequest(message);

        verify(productService).getAllProductsFromUser(any());
    }

    @Test
    void handle_request_delete_product() {
        when(messageProperties.getType()).thenReturn(DELETE_PRODUCT.name());
        when(message.getBody()).thenReturn(UUID.randomUUID().toString().getBytes());


        listener.handleRequest(message);

        try {
            verify(productService).deleteProduct(any());
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void handle_request_create_product() {
        when(messageProperties.getType()).thenReturn(CREATE_PRODUCT.name());
        when(message.getBody()).thenReturn(new Gson().toJson(new Product()).getBytes());


        listener.handleRequest(message);

        try {
            verify(productService).createProduct(any());
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void handle_request_update_product() {
        when(messageProperties.getType()).thenReturn(UPDATE_PRODUCT.name());
        when(message.getBody()).thenReturn(new Gson().toJson(new Product()).getBytes());


        listener.handleRequest(message);

        try {
            verify(productService).updateProduct(any());
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void handle_request_invalid_type() {
        when(messageProperties.getType()).thenReturn("invalid type");

        listener.handleRequest(message);

        verifyNoInteractions(productService);
    }
}
