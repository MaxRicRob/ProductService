package com.example.ProductServiceApplication.repository.jpa;

import com.example.ProductServiceApplication.domain.ProductComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductComponentEntityDatabaseLoader {

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    @Bean
    CommandLineRunner initDatabase(ProductComponentEntityJpaRepository repository) {
        return args -> {
            log.info("initiating ProductComponentEntityDatabaseLoader");
            List<ProductComponentEntity> productComponents = getProductComponentEntitiesFromWarehouse();
            List<ProductComponentEntity> productComponentsWithoutDuplicates = removeDuplicates(repository, productComponents);
            repository.saveAll(productComponentsWithoutDuplicates);
            log.info("ProductComponentEntityDatabaseLoader initiated");
        };
    }

    private List<ProductComponentEntity> getProductComponentEntitiesFromWarehouse() {

        var productComponentFlux = webClient
                .get()
                .uri("components")
                .retrieve()
                .bodyToFlux(ProductComponent.class);

        productComponentFlux.subscribe();

        return productComponentFlux.toStream()
                .map(ProductComponentEntity::from)
                .collect(Collectors.toList());
    }

    private List<ProductComponentEntity> removeDuplicates(ProductComponentEntityJpaRepository repository, List<ProductComponentEntity> productComponents) {

        var idsOfPresentEntitiesInWarehouse = getIdsOfPresentEntitiesInWarehouse(repository);

        return productComponents.stream()
                .filter(p -> !idsOfPresentEntitiesInWarehouse.contains(p.getId()))
                .collect(Collectors.toList());
    }

    private List<Integer> getIdsOfPresentEntitiesInWarehouse(ProductComponentEntityJpaRepository repository) {
        return repository
                .findAll()
                .stream()
                .map(ProductComponentEntity::getId)
                .collect(Collectors.toList());
    }
}
