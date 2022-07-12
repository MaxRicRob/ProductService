package com.example.ProductServiceApplication.repository;



import com.example.ProductServiceApplication.domain.entity.DefaultProduct;

import java.util.List;

public interface DefaultProductRepository {

    List<DefaultProduct> findAll();

}
