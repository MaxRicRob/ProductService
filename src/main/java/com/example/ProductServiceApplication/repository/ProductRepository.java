package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.repository.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    List<ProductEntity> findProductByUserName(String userName);

    void createProduct(ProductEntity product) throws ErrorResponseException;

    void updateProduct(ProductEntity product) throws ErrorResponseException;

    void deleteProduct(UUID uuid) throws ErrorResponseException;
}

