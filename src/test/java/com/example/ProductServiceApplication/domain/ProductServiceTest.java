package com.example.ProductServiceApplication.domain;

import com.example.ProductServiceApplication.api.error.ErrorResponseException;
import com.example.ProductServiceApplication.entity.Product;
import com.example.ProductServiceApplication.repository.DefaultProductRepository;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
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
class ProductServiceTest {

    public static final String TEST = "test";
    @InjectMocks
    private ProductService productService;
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
        productService.getAllProductComponents();

        verify(productComponentRepository).findAll();

    }

    @Test
    void getAllDefaultProducts() {
        productService.getAllDefaultProducts();

        verify(defaultProductRepository).findAll();
    }

    @Test
    void getAllProductsFromUser() {
        productService.getAllProductsFromUser(TEST);

        verify(productRepository).findProductByUserName(eq(TEST));
    }

    @Test
    void createProduct() {
        try {
            productService.createProduct(product);
            verify(productRepository).createProduct(any(Product.class));
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void updateProduct() {
        try {
            productService.updateProduct(product);
            verify(productRepository).updateProduct(any(Product.class));
        } catch (ErrorResponseException e) {
            fail();
        }
    }

    @Test
    void deleteProduct() {
        try {
            productService.deleteProduct(UUID.randomUUID());
            verify(productRepository).deleteProduct(any(UUID.class));
        } catch (ErrorResponseException e) {
            fail();
        }
    }
}
