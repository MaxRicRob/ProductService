package com.example.ProductServiceApplication.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
public class DefaultProductResponse {

    private int id;
    private String name;
    private List<ProductComponentResponse> components;

}
