package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.entity.Product;
import com.example.ProductServiceApplication.entity.ProductEntity;
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
    public List<Product> findProductByUserName(String userName) {
        return productEntityJpaRepository.findAll().stream()
                .filter(productEntity -> productEntity.getUserName().equals(userName))
                .map(Product::from)
                .collect(Collectors.toList());
    }

    @Override
    public void createProduct(Product product) {

        if (productEntityJpaRepository.findById(product.getId()).isEmpty()) {
            productEntityJpaRepository.save(ProductEntity.from(product));
        } else {
            log.warn("can't insert because product already exists in database");
        }
    }

    @Override
    public void updateProduct(Product product) {

        var updatedProduct = ProductEntity.from(product);

        productEntityJpaRepository.findById(product.getId())
                .map(productToUpdate -> {
                    productToUpdate.setComponents(updatedProduct.getComponents());
                    productToUpdate.setName(updatedProduct.getName());
                    productToUpdate.setUserName(updatedProduct.getUserName());
                    return this.productEntityJpaRepository.save(productToUpdate);
                });
    }

    @Override
    public void deleteProduct(UUID uuid) {
        if (productEntityJpaRepository.findById(uuid).isPresent()) {
            productEntityJpaRepository.deleteById(uuid);
        } else {
            log.warn("can't insert because product doesn't exists in database");

        }

    }
}
