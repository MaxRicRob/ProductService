package com.example.ProductServiceApplication.repository.impl;

import com.example.ProductServiceApplication.repository.ProductComponentRepository;
import com.example.ProductServiceApplication.repository.entity.ProductComponentEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductComponentEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductComponentRepositoryImpl implements ProductComponentRepository {

    private final ProductComponentEntityJpaRepository productComponentEntityJpaRepository;

    @Cacheable(value = "productComponentCache")
    @Override
    public List<ProductComponentEntity> findAll() {
        log.info("get components from repo without cache");

        return productComponentEntityJpaRepository.findAll();
    }
}
