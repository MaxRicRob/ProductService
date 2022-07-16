package com.example.ProductServiceApplication.domain;

import com.example.ProductServiceApplication.entity.PriceRequest;
import com.example.ProductServiceApplication.entity.PriceResponse;

public interface PriceService {

    PriceResponse getPrice(PriceRequest priceRequest);
}
