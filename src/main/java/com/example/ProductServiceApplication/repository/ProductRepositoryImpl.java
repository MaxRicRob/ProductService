package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.repository.jpa.ProductEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{

    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<Product> findByUserName(String userName) {
        return productJpaRepository.findAll().stream()
                .filter(productEntity -> productEntity.getName().equals(userName))
                .map(Product::from)
                .collect(Collectors.toList());
    }

    @Override
    public void insertProduct(Product product) {

        productJpaRepository.save(ProductEntity.from(product));
    }

    @Override
    public Product updateProduct(Product product) {
        return product;
    }
}
