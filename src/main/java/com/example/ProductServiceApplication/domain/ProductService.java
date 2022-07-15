package com.example.ProductServiceApplication.domain;

import com.example.ProductServiceApplication.domain.entity.DefaultProduct;
import com.example.ProductServiceApplication.domain.entity.Product;
import com.example.ProductServiceApplication.domain.entity.ProductComponent;
import com.example.ProductServiceApplication.error.ErrorResponseException;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductComponent> getAllProductComponents();

    List<DefaultProduct> getAllDefaultProducts();

    List<Product> getAllProductsFromUser(String userName);

    Product createProduct(Product product) throws ErrorResponseException;

    Product updateProduct(Product product) throws ErrorResponseException;

    void deleteProduct(UUID uuid) throws ErrorResponseException;

}
