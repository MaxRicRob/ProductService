package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductEntityJpaRepository extends JpaRepository<ProductEntity, UUID> {
}
