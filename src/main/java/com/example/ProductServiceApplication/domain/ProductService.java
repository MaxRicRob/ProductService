package com.example.ProductServiceApplication.domain;


import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.domain.entity.DefaultProduct;
import com.example.ProductServiceApplication.domain.entity.PriceRequest;
import com.example.ProductServiceApplication.domain.entity.Product;
import com.example.ProductServiceApplication.domain.entity.ProductComponent;
import com.example.ProductServiceApplication.repository.DefaultProductRepository;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductComponentRepository productComponentRepository;
    private final ProductRepository productRepository;
    private final DefaultProductRepository defaultProductRepository;
    private final PriceService priceService;

    public List<ProductComponent> getAllProductComponents() {
        return productComponentRepository.findAll();
    }

    public List<DefaultProduct> getAllDefaultProducts() {
        return defaultProductRepository.findAll();
    }


    public List<Product> getAllProductsFromUser(String userName) {

        var products = productRepository.findProductByUserName(userName);
        products.forEach(setTotalPrice());

        return products;
    }

    public Product createProduct(Product product) throws ErrorResponseException {
        product.setId(UUID.randomUUID());
        return productRepository.createProduct(product);
    }

    public void updateProduct(Product product) throws ErrorResponseException {
        productRepository.updateProduct(product);
    }

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
}
