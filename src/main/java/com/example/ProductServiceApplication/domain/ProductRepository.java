package com.example.ProductServiceApplication.domain;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    List<Product> findProductByUserName(String userName);

    void createProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(UUID uuid);
}

