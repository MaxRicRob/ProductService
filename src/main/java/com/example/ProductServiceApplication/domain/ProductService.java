package com.example.ProductServiceApplication.domain;


import com.example.ProductServiceApplication.entity.DefaultProduct;
import com.example.ProductServiceApplication.entity.Product;
import com.example.ProductServiceApplication.entity.ProductComponent;
import com.example.ProductServiceApplication.entity.ProductResponse;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductComponentRepository productComponentRepository;
    private final ProductRepository productRepository;
    private final WebClient webClient = WebClient.create("http://localhost:8081");


    public List<ProductComponent> getAllProductComponents() {
        return productComponentRepository.findAll();
    }

    public List<DefaultProduct> getAllDefaultProducts() {

        var defaultProductFlux = webClient
                .get()
                .uri("defaultProducts")
                .retrieve()
                .bodyToFlux(DefaultProduct.class);
        defaultProductFlux.subscribe();

        return defaultProductFlux
                .toStream()
                .collect(Collectors.toList());
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
