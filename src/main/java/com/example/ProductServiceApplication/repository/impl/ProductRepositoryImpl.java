package com.example.ProductServiceApplication.repository.impl;

import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.repository.ProductRepository;
import com.example.ProductServiceApplication.repository.entity.ProductEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductEntityJpaRepository productEntityJpaRepository;

    @Override
    public List<ProductEntity> findProductByUserName(String userName) {

        return productEntityJpaRepository
                .findAll()
                .stream()
                .filter(productEntity -> productEntity.getUserName().equals(userName))
                .collect(Collectors.toList());
    }

    @Override
    public void createProduct(ProductEntity productEntity) throws ErrorResponseException {

        if (productEntityIsPresent(productEntity)) {
            log.error("productEntity with id: {} already exists in database", productEntity.getId());
            throw new ErrorResponseException();
        }
        productEntityJpaRepository.save(productEntity);
    }

    @Override
    public void updateProduct(ProductEntity productEntity) throws ErrorResponseException {

        if (!productEntityIsPresent(productEntity)) {
            trackError(productEntity.getId());
            throw new ErrorResponseException();
        }
        productEntityJpaRepository.findById(productEntity.getId())
                .map(productToUpdate -> {
                    productToUpdate.setComponents(productEntity.getComponents());
                    productToUpdate.setName(productEntity.getName());
                    productToUpdate.setUserName(productEntity.getUserName());
                    return this.productEntityJpaRepository.save(productToUpdate);
                });
    }

    @Override
    public void deleteProduct(UUID uuid) throws ErrorResponseException {
        if (!productEntityIsPresent(uuid)) {
            trackError(uuid);
            throw new ErrorResponseException();
        }
        productEntityJpaRepository.deleteById(uuid);
    }

    private void trackError(UUID uuid) {
        log.error("can't find product with id: {} in database", uuid);
    }

    private boolean productEntityIsPresent(UUID uuid) {
        return productEntityJpaRepository.findById(uuid).isPresent();
    }

    private boolean productEntityIsPresent(ProductEntity product) {
        return productEntityJpaRepository.findById(product.getId()).isPresent();
    }
}
