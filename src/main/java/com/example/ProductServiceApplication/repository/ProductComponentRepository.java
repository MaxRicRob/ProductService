package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.domain.ProductComponent;

import java.util.List;

public interface ProductComponentRepository {

    List<ProductComponent> findAll();
}

