package com.example.ProductServiceApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
public class ProductEntity {

    @Id()
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userName;
    @Column
    @ManyToMany
    @JoinTable
    private List<ProductComponentEntity> components;

    public static ProductEntity from(Product product) {
        return new ProductEntity()
                .setId(product.getId())
                .setName(product.getName())
                .setUserName(product.getUserName())
                .setComponents(getProductComponentEntities(product));
    }

    private static List<ProductComponentEntity> getProductComponentEntities(Product product) {
        return product.getComponents().stream()
                .map(ProductComponentEntity::from)
                .collect(Collectors.toList());
    }
}
