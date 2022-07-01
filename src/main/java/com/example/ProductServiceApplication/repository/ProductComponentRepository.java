package com.example.ProductServiceApplication.repository;

import com.example.ProductServiceApplication.entity.ProductComponent;

import java.util.List;

public interface ProductComponentRepository {

    List<ProductComponent> findAll();
}

