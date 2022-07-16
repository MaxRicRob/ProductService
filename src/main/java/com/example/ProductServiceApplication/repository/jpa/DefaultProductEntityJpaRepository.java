package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.entity.DefaultProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultProductEntityJpaRepository extends JpaRepository<DefaultProductEntity, Integer> {
}
