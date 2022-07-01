package com.example.ProductServiceApplication.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@JsonPropertyOrder({"id", "name", "price", "weight", "color", "origin", "awesomeness", "farmer", "organic", "calories"})
public class ProductComponentEntity {

    @Id
    @Column(nullable = false, unique = true)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private long price;
    @Column(nullable = false)
    private int weight;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String origin;
    @Column(nullable = false)
    private int awesomeness;
    @Column(nullable = false)
    private String farmer;
    @Column(nullable = false)
    private boolean organic;
    @Column(nullable = false)
    private int calories;

    public static ProductComponentEntity from(ProductComponent productComponent) {
        return new ProductComponentEntity()
                .setId(productComponent.getId())
                .setName(productComponent.getName())
                .setPrice(productComponent.getPrice())
                .setWeight(productComponent.getWeight())
                .setColor(productComponent.getColor())
                .setOrigin(productComponent.getOrigin())
                .setAwesomeness(productComponent.getAwesomeness())
                .setFarmer(productComponent.getFarmer())
                .setOrganic(productComponent.isOrganic())
                .setCalories(productComponent.getCalories());
    }

}
