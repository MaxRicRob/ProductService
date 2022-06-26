package com.example.ProductServiceApplication.service;

import com.example.ProductServiceApplication.domain.Product;
import com.example.ProductServiceApplication.domain.ProductComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductResponse {

    private UUID id;
    private String name;
    private String userName;
    private List<ProductComponent> productComponents;

    public static ProductResponse from(Product product) {
        return new ProductResponse()
                .setId(product.getId())
                .setName(product.getName())
                .setUserName(product.getUserName())
                .setProductComponents(product.getProductComponents());
    }

}
