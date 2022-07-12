package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.domain.entity.ProductComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductComponentEntityJpaRepository extends JpaRepository<ProductComponentEntity, Long> {
}
