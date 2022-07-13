package com.example.ProductServiceApplication.repository.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

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

        var productComponentFlux = webClient
                .get()
                .uri("components")
                .retrieve()
                .bodyToFlux(ProductComponentEntity.class);

        productComponentFlux.subscribe();
        log.info("Product Components received from Warehouse");

        return productComponentFlux
                .toStream()
                .collect(Collectors.toList());
    }

    private List<DefaultProductEntity> getDefaultProductEntitiesFromWarehouse() {
        var defaultProductFlux = webClient
                .get()
                .uri("defaultProducts")
                .retrieve()
                .bodyToFlux(DefaultProductEntity.class);
        defaultProductFlux.subscribe();
        log.info("Default Product received from Warehouse");

        return defaultProductFlux
                .toStream()
                .collect(Collectors.toList());
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
