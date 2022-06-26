package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.domain.ProductComponent;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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
        productComponentRepository.insert(
                new ProductComponent()
                .setPrice(1)
                .setName("abc")
                .setAwesomeness(1)
                .setCalories(1)
                .setFarmer("test")
                .setWeight(1)
                .setOrganic(true)
                .setColor("blue")
                .setOrigin("da")
                .setId(0)

        );
        productRepository.insertProduct(new Product()
                .setId(UUID.randomUUID())
                .setName("test")
                .setUserName("test")
                .setProductComponents(getComponents()));
    }

    private List<ProductComponent> getComponents() {
        return List.of(
                new ProductComponent()
                        .setPrice(1)
                        .setName("abc")
                        .setAwesomeness(1)
                        .setCalories(1)
                        .setFarmer("test")
                        .setWeight(1)
                        .setOrganic(true)
                        .setColor("blue")
                        .setOrigin("da")
                        .setId(0)


        );
    }


}
