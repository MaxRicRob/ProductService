package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.ProductComponent;
import com.example.ProductServiceApplication.repository.jpa.ProductComponentEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductComponentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductComponentRepositoryImpl implements ProductComponentRepository {

    private final ProductComponentJpaRepository productComponentJpaRepository;

    @Override
    public List<ProductComponent> findAll() {
        return productComponentJpaRepository.findAll().stream()
                .map(ProductComponent::from)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(ProductComponent productComponent) {
        productComponentJpaRepository.save(ProductComponentEntity.from(productComponent));
    }
}
