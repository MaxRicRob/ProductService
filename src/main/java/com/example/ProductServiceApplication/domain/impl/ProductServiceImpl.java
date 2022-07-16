package com.example.ProductServiceApplication.domain.impl;


import com.example.ProductServiceApplication.domain.PriceService;
import com.example.ProductServiceApplication.domain.ProductService;
import com.example.ProductServiceApplication.domain.entity.DefaultProduct;
import com.example.ProductServiceApplication.domain.entity.PriceRequest;
import com.example.ProductServiceApplication.domain.entity.Product;
import com.example.ProductServiceApplication.domain.entity.ProductComponent;
import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.repository.DefaultProductRepository;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import com.example.ProductServiceApplication.repository.entity.ProductComponentEntity;
import com.example.ProductServiceApplication.repository.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductComponentRepository productComponentRepository;
    private final ProductRepository productRepository;
    private final DefaultProductRepository defaultProductRepository;
    private final PriceService priceService;

    @Override
    public List<ProductComponent> getAllProductComponents() {
        return productComponentRepository.findAll();

    }

    @Override
    public List<DefaultProduct> getAllDefaultProducts() {
        return defaultProductRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsFromUser(String userName) {

        var products = productRepository
                .findProductByUserName(userName)
                .stream()
                .map(Product::from)
                .collect(Collectors.toList());
        products.forEach(setTotalPrice());

        return products;
    }

    @Override
    public Product createProduct(Product product) throws ErrorResponseException {
        product.setId(UUID.randomUUID());
        productRepository.createProduct(toProductEntity(product));
        return product;
    }

    @Override
    public Product updateProduct(Product product) throws ErrorResponseException {
        productRepository.updateProduct(toProductEntity(product));
        return product;
    }

    @Override
    public void deleteProduct(UUID uuid) throws ErrorResponseException {
        productRepository.deleteProduct(uuid);
    }

    private Consumer<Product> setTotalPrice() {
        return p -> p.setTotalPrice(
                priceService.getPrice(
                        new PriceRequest()
                                .setPrices(getComponentPrices(p))
                ).getTotalPrice()
        );
    }

    private List<Long> getComponentPrices(Product product) {
        return product.getComponents().stream()
                .map(ProductComponent::getPrice)
                .collect(Collectors.toList());
    }

    private ProductEntity toProductEntity(Product product) {

        return new ProductEntity()
                .setId(product.getId())
                .setName(product.getName())
                .setUserName(product.getUserName())
                .setComponents(
                        product.getComponents()
                                .stream()
                                .map(this::toProductComponentEntity)
                                .collect(Collectors.toList())
                );

    }

    private ProductComponentEntity toProductComponentEntity(ProductComponent productComponent) {

        return new ProductComponentEntity()
                .setId(productComponent.getId())
                .setName(productComponent.getName())
                .setPrice(productComponent.getPrice())
                .setWeight(productComponent.getWeight())
                .setColor(productComponent.getColor())
                .setOrigin(productComponent.getOrigin())
                .setAwesomeness(productComponent.getAwesomeness())
                .setFarmer(productComponent.getFarmer())
                .setOrganic(productComponent.isOrganic())
                .setCalories(productComponent.getCalories());

    }
}
