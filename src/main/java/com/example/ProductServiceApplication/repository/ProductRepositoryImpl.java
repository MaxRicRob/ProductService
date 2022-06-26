package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.repository.jpa.ProductEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{

    private final ProductEntityJpaRepository productEntityJpaRepository;

    @Override
    public List<Product> findByUserName(String userName) {
        return productEntityJpaRepository.findAll().stream()
                .filter(productEntity -> productEntity.getName().equals(userName))
                .map(Product::from)
                .collect(Collectors.toList());
    }

    @Override
    public void insertProduct(Product product) {

        productEntityJpaRepository.save(ProductEntity.from(product));
    }

    @Override
    public Product updateProduct(Product product) {
        return product;
    }
}
