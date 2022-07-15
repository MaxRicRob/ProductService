package com.example.ProductServiceApplication.domain.entity;

import com.example.ProductServiceApplication.repository.entity.ProductEntity;
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
public class Product {

    private UUID id;
    private String name;
    private String userName;
    private long totalPrice;
    private List<ProductComponent> components;

    public static Product from(ProductEntity productEntity) {
        return new Product()
                .setId(productEntity.getId())
                .setName(productEntity.getName())
                .setUserName(productEntity.getUserName())
                .setComponents(getComponents(productEntity));
    }

    private static List<ProductComponent> getComponents(ProductEntity productEntity) {
        return productEntity.getComponents().stream()
                .map(ProductComponent::from)
                .collect(Collectors.toList());
    }

}
