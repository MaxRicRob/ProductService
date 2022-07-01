package com.example.ProductServiceApplication.utils;

import com.example.ProductServiceApplication.repository.jpa.ProductEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final ProductEntityJpaRepository productEntityJpaRepository;


    @GetMapping("/deleteProducts")
    public String deleteAll() {
        productEntityJpaRepository.deleteAll();
        return "done deleting all products";
    }
}
