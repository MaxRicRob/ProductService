package com.example.ProductServiceApplication.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DefaultProduct {

    private int id;
    private String name;
    private List<ProductComponent> components;

    public static DefaultProduct from(DefaultProductEntity defaultProductEntity) {
        return new DefaultProduct()
                .setId(defaultProductEntity.getId())
                .setName(defaultProductEntity.getName())
                .setComponents(defaultProductEntity.getComponents()
                        .stream()
                        .map(ProductComponent::from)
                        .collect(Collectors.toList())
                );
    }

}
