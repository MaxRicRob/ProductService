package com.example.ProductServiceApplication.repository;


import com.example.ProductServiceApplication.repository.entity.DefaultProductEntity;

import java.util.List;

public interface DefaultProductRepository {

    List<DefaultProductEntity> findAll();

}
