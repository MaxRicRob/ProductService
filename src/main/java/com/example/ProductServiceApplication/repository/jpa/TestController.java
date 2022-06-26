package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ProductRepository productRepository;
    private final ProductComponentRepository productComponentRepository;

    @GetMapping("/")
    public List<Product> getProduct() {
        return productRepository.findByUserName("test");
    }

    @PostMapping("/")
    public void insert() {
        productRepository.insertProduct(new Product()
                .setId(UUID.randomUUID())
                .setName("test")
                .setUserName("test")
                .setProductComponents(productComponentRepository.findAll().stream().filter(p -> p.getId()==1).collect(Collectors.toList())));
    }
}
