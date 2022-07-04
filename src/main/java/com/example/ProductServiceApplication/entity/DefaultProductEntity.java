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
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
public class DefaultProductEntity {


    @Id
    @Column(nullable = false, unique = true)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column
    @ManyToMany
    @JoinTable
    private List<ProductComponentEntity> components;


    public static DefaultProductEntity from(DefaultProductResponse defaultProductResponse) {
        return new DefaultProductEntity()
                .setId(defaultProductResponse.getId())
                .setName(defaultProductResponse.getName())
                .setComponents(defaultProductResponse.getComponents()
                        .stream()
                        .map(ProductComponentEntity::from)
                        .collect(Collectors.toList())
                );
    }
}
