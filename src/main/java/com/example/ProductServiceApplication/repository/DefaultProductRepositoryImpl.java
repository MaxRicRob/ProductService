package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.entity.DefaultProduct;
import com.example.ProductServiceApplication.repository.jpa.DefaultProductEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultProductRepositoryImpl implements DefaultProductRepository {

    private final DefaultProductEntityJpaRepository defaultProductEntityJpaRepository;

    @Cacheable(value = "defaultProductCache")
    @Override
    public List<DefaultProduct> findAll() {
        log.info("get default products from repo without cache");
        return defaultProductEntityJpaRepository.findAll().stream()
                .map(DefaultProduct::from)
                .collect(Collectors.toList());
    }
}
