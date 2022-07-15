package com.example.ProductServiceApplication.domain;

import com.example.ProductServiceApplication.domain.entity.PriceRequest;
import com.example.ProductServiceApplication.domain.entity.PriceResponse;

public interface PriceService {

    PriceResponse getPrice(PriceRequest priceRequest);
}
