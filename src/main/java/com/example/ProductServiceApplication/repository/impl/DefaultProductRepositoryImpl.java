package com.example.ProductServiceApplication.repository.impl;

import com.example.ProductServiceApplication.repository.DefaultProductRepository;
import com.example.ProductServiceApplication.repository.entity.DefaultProductEntity;
import com.example.ProductServiceApplication.repository.jpa.DefaultProductEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultProductRepositoryImpl implements DefaultProductRepository {

    private final DefaultProductEntityJpaRepository defaultProductEntityJpaRepository;

    @Cacheable(value = "defaultProductCache")
    @Override
    public List<DefaultProductEntity> findAll() {
        log.info("get default products from repo without cache");
        return defaultProductEntityJpaRepository.findAll();
    }
}
