package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    List<Product> findProductByUserName(String userName);

    void insertProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(UUID uuid);
}

