package com.example.ProductServiceApplication.domain;

import com.example.ProductServiceApplication.repository.jpa.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DefaultProduct {

    private int id;
    private String name;
    private List<ProductComponent> productComponents;

}
