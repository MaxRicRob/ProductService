package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.repository.entity.ProductComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductComponentEntityJpaRepository extends JpaRepository<ProductComponentEntity, Long> {
}
