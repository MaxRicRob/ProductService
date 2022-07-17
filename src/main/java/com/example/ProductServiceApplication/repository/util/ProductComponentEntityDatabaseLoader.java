package com.example.ProductServiceApplication.repository.util;

import com.example.ProductServiceApplication.entity.DefaultProductEntity;
import com.example.ProductServiceApplication.entity.ProductComponentEntity;
import com.example.ProductServiceApplication.repository.jpa.DefaultProductEntityJpaRepository;
import com.example.ProductServiceApplication.repository.jpa.ProductComponentEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductComponentEntityDatabaseLoader {

    private final WebClient webClient = WebClient.create("http://WarehouseApp:8081");


    @Bean
    CommandLineRunner initDatabase(ProductComponentEntityJpaRepository productComponentEntityJpaRepository,
                                   DefaultProductEntityJpaRepository defaultProductEntityJpaRepository) {
        return args -> {
            log.info("initiating ProductService DatabaseLoader");
            var productComponents = getProductComponentEntitiesFromWarehouse();
            var productComponentsWithoutDuplicates = removeDuplicates(
                    productComponentEntityJpaRepository,
                    productComponents
            );
            productComponentEntityJpaRepository.saveAll(productComponentsWithoutDuplicates);

            var defaultProducts = getDefaultProductEntitiesFromWarehouse();
            var defaultProductsWithoutDuplicates = removeDuplicates(
                    defaultProductEntityJpaRepository,
                    defaultProducts
            );
            defaultProductEntityJpaRepository.saveAll(defaultProductsWithoutDuplicates);
            log.info("ProductService DatabaseLoader initiated");
        };
    }

    private List<ProductComponentEntity> getProductComponentEntitiesFromWarehouse() {

        Mono<List<ProductComponentEntity>> response = webClient
                .get()
                .uri("components")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        log.info("Product Components received from Warehouse");

        return response.block();
    }

    private List<DefaultProductEntity> getDefaultProductEntitiesFromWarehouse() {
        Mono<List<DefaultProductEntity>> response = webClient
                .get()
                .uri("defaultProducts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        log.info("Default Product received from Warehouse");

        return response.block();
    }


    private List<ProductComponentEntity> removeDuplicates(ProductComponentEntityJpaRepository repository, List<ProductComponentEntity> productComponents) {

        var idsOfPresentEntitiesInWarehouse = getIdsOfPresentEntitiesInWarehouse(repository);

        return productComponents.stream()
                .filter(p -> !idsOfPresentEntitiesInWarehouse.contains(p.getId()))
                .collect(Collectors.toList());
    }

    private List<DefaultProductEntity> removeDuplicates(DefaultProductEntityJpaRepository repository, List<DefaultProductEntity> jpaEntities) {

        var idsOfPresentEntities = getIdsOfPresentEntities(repository);

        return jpaEntities.stream()
                .filter(p -> !idsOfPresentEntities.contains(p.getId()))
                .collect(Collectors.toList());
    }

    private List<Integer> getIdsOfPresentEntitiesInWarehouse(ProductComponentEntityJpaRepository repository) {
        return repository
                .findAll()
                .stream()
                .map(ProductComponentEntity::getId)
                .collect(Collectors.toList());
    }

    private List<Integer> getIdsOfPresentEntities(DefaultProductEntityJpaRepository repository) {
        return repository
                .findAll()
                .stream()
                .map(DefaultProductEntity::getId)
                .collect(Collectors.toList());
    }
}
