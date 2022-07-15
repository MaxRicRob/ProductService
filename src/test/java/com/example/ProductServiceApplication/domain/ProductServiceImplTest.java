package com.example.ProductServiceApplication.domain;

import com.example.ProductServiceApplication.domain.entity.Product;
import com.example.ProductServiceApplication.domain.impl.ProductServiceImpl;
import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.repository.DefaultProductRepository;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import com.example.ProductServiceApplication.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    public static final String TEST = "test";
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    @Mock
    private ProductComponentRepository productComponentRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private DefaultProductRepository defaultProductRepository;
    @Mock
    private Product product;


    @Test
    void getAllProductComponents() {
        productServiceImpl.getAllProductComponents();

        verify(productComponentRepository).findAll();

    }

    @Test
    void getAllDefaultProducts() {
        productServiceImpl.getAllDefaultProducts();

        verify(defaultProductRepository).findAll();
    }

    @Test
    void getAllProductsFromUser() {
        productServiceImpl.getAllProductsFromUser(TEST);

        verify(productRepository).findProductByUserName(eq(TEST));
    }

    @Test
    void createProduct() {
        try {
            productServiceImpl.createProduct(product);

            verify(productRepository).createProduct(any(ProductEntity.class));
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void updateProduct() {
        try {
            productServiceImpl.updateProduct(product);

            verify(productRepository).updateProduct(any(ProductEntity.class));
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void deleteProduct() {
        try {
            productServiceImpl.deleteProduct(UUID.randomUUID());

            verify(productRepository).deleteProduct(any(UUID.class));
        } catch (ErrorResponseException e) {
            fail();
        }
    }
}
