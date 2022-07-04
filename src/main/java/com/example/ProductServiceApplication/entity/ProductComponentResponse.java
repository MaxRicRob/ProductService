package com.example.ProductServiceApplication.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductComponentResponse {

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

    public static ProductComponentResponse from(ProductComponent productComponent) {
        return new ProductComponentResponse()
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
