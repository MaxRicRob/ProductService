package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.error.ErrorResponseException;
import com.example.ProductServiceApplication.domain.entity.Product;
import com.example.ProductServiceApplication.repository.jpa.ProductEntity;
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

        return productEntityJpaRepository
                .findAll()
                .stream()
                .filter(productEntity -> productEntity.getUserName().equals(userName))
                .map(Product::from)
                .collect(Collectors.toList());
    }

    @Override
    public Product createProduct(Product product) throws ErrorResponseException {

        if (productIsPresent(product)) {
            log.error("product with id: {} already exists in database", product.getId());
            throw new ErrorResponseException();
        }
        return Product.from(productEntityJpaRepository.save(ProductEntity.from(product)));
    }

    @Override
    public void updateProduct(Product product) throws ErrorResponseException {

        if (!productIsPresent(product)) {
            trackError(product.getId());
            throw new ErrorResponseException();
        }

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
    public void deleteProduct(UUID uuid) throws ErrorResponseException {
        if (!productIsPresent(uuid)) {
            trackError(uuid);
            throw new ErrorResponseException();
        }
        productEntityJpaRepository.deleteById(uuid);
    }

    private void trackError(UUID uuid) {
        log.error("can't find product with id: {} in database", uuid);
    }

    private boolean productIsPresent(UUID uuid) {
        return productEntityJpaRepository.findById(uuid).isPresent();
    }

    private boolean productIsPresent(Product product) {
        return productEntityJpaRepository.findById(product.getId()).isPresent();
    }
}
