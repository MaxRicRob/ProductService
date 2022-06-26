package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.repository.jpa.ProductEntity;
import com.example.ProductServiceApplication.repository.jpa.ProductEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{

    private final ProductEntityJpaRepository productEntityJpaRepository;

    @Override
    public List<Product> findProductByUserName(String userName) {
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
    public void updateProduct(Product product) {

        productEntityJpaRepository
                .findById(product.getId())
                .ifPresentOrElse(p -> updateProductEntity(p,ProductEntity.from(product)),
                        () -> log.info("product not found")
                );
    }

    @Override
    public void deleteProduct(UUID uuid) {
        productEntityJpaRepository.deleteById(uuid);
    }

    private void updateProductEntity(ProductEntity productEntityToUpdate, ProductEntity updatedProductEntity) {

        productEntityToUpdate
                .setProductComponentsEntities(updatedProductEntity.getProductComponentsEntities())
                .setName(updatedProductEntity.getName())
                .setUserName(updatedProductEntity.getUserName());
        productEntityJpaRepository.save(productEntityToUpdate);
    }
}
