package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.repository.jpa.ProductEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductJpaRepository;

import java.util.List;

public interface ProductRepository {

    List<Product> findByUserName(String userName);

    void insertProduct(Product product);

    Product updateProduct(Product product);

}

