package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.domain.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    List<Product> findProductByUserName(String userName);

    Product createProduct(Product product) throws ErrorResponseException;

    void updateProduct(Product product) throws ErrorResponseException;

    void deleteProduct(UUID uuid) throws ErrorResponseException;
}

