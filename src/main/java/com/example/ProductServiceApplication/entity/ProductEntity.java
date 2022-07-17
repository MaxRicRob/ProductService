package com.example.ProductServiceApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private List<ProductComponentEntity> components;

}
