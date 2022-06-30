package com.example.ProductServiceApplication.service;


import com.example.ProductServiceApplication.domain.DefaultProduct;
import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.domain.ProductComponent;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import com.example.ProductServiceApplication.repository.jpa.ProductComponentEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
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

        Flux<DefaultProduct> defaultProductFlux = webClient
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

    public void addProduct(Product product) {
        productRepository.insertProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(UUID uuid) {
        productRepository.deleteProduct(uuid);
    }
}
