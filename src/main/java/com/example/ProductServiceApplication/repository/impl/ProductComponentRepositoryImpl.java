package com.example.ProductServiceApplication.repository.impl;

import com.example.ProductServiceApplication.entity.ProductComponent;
import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.jpa.ProductComponentEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductComponentRepositoryImpl implements ProductComponentRepository {

    private final ProductComponentEntityJpaRepository productComponentEntityJpaRepository;

    @Override
    public List<ProductComponent> findAll() {
        return productComponentEntityJpaRepository
                .findAll()
                .stream()
                .map(ProductComponent::from)
                .collect(Collectors.toList());
    }
}
