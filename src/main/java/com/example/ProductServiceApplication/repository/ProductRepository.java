package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findByUserName(String userName);

    void insertProduct(Product product);

    Product updateProduct(Product product);
}

