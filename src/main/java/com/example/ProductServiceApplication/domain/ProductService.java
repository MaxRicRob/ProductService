package com.example.ProductServiceApplication.domain;


import com.example.ProductServiceApplication.entity.DefaultProduct;
import com.example.ProductServiceApplication.entity.Product;
import com.example.ProductServiceApplication.entity.ProductComponent;
import com.example.ProductServiceApplication.entity.ProductResponse;
import com.example.ProductServiceApplication.repository.DefaultProductRepository;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductComponentRepository productComponentRepository;
    private final ProductRepository productRepository;
    private final DefaultProductRepository defaultProductRepository;


    public List<ProductComponent> getAllProductComponents() {
        return productComponentRepository.findAll();
    }

    public List<DefaultProduct> getAllDefaultProducts() {
        return defaultProductRepository.findAll();
    }


    public List<ProductResponse> getAllProductsFromUser(String userName) {
        return productRepository
                .findProductByUserName(userName)
                .stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public void createProduct(Product product) {
        productRepository.createProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(UUID uuid) {
        productRepository.deleteProduct(uuid);
    }
}
