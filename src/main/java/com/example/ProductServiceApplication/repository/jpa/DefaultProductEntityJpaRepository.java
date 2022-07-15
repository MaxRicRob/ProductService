package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.repository.entity.DefaultProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultProductEntityJpaRepository extends JpaRepository<DefaultProductEntity, Integer> {
}
