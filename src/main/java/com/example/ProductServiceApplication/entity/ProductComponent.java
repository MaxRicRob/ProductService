package com.example.ProductServiceApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductComponent implements Serializable {

    private int id;
    private String name;
    private long price;
    private int weight;
    private String color;
    private String origin;
    private int awesomeness;
    private String farmer;
    private boolean organic;
    private int calories;

    public static ProductComponent from(ProductComponentEntity productComponentEntity) {
        return new ProductComponent()
                .setId(productComponentEntity.getId())
                .setName(productComponentEntity.getName())
                .setPrice(productComponentEntity.getPrice())
                .setWeight(productComponentEntity.getWeight())
                .setColor(productComponentEntity.getColor())
                .setOrigin(productComponentEntity.getOrigin())
                .setAwesomeness(productComponentEntity.getAwesomeness())
                .setFarmer(productComponentEntity.getFarmer())
                .setOrganic(productComponentEntity.isOrganic())
                .setCalories(productComponentEntity.getCalories());
    }

}
