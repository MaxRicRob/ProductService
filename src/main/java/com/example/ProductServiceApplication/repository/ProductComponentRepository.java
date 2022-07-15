package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.repository.entity.ProductComponentEntity;

import java.util.List;

public interface ProductComponentRepository {

    List<ProductComponentEntity> findAll();
}

