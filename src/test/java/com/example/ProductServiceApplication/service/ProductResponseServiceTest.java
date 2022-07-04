package com.example.ProductServiceApplication.service;

import com.example.ProductServiceApplication.entity.DefaultProduct;
import com.example.ProductServiceApplication.domain.ProductService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseServiceTest {

    @Test
    void getAllDefaultProducts() {

        ProductService productService = new ProductService(null, null, null);

        List<DefaultProduct> allDefaultProducts = productService.getAllDefaultProducts();

        assertThat(allDefaultProducts.size()).isEqualTo(5);

    }

}
