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
public class Product {

    private UUID id;
    private String name;
    private String userName;
    private List<ProductComponent> productComponents;

    public static Product from(ProductEntity productEntity) {
        return new Product()
                .setId(productEntity.getId())
                .setName(productEntity.getName())
                .setUserName(productEntity.getUserName())
                .setProductComponents(getProductComponents(productEntity));
    }

    private static List<ProductComponent> getProductComponents(ProductEntity productEntity) {
        return productEntity.getProductComponentsEntities().stream()
                .map(ProductComponent::from)
                .collect(Collectors.toList());
    }

}
