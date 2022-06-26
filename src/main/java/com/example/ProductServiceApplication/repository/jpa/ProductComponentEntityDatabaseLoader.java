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
    CommandLineRunner initDatabase(ProductComponentEntityJpaRepository productComponentEntityJpaRepository) {
        return args -> {

            log.info("initiating ProductComponentEntityDatabaseLoader");
            Flux<ProductComponent> productComponentFlux = webClient
                    .get()
                    .uri("components")
                    .retrieve()
                    .bodyToFlux(ProductComponent.class);
            productComponentFlux.subscribe();
            List<ProductComponentEntity> productComponents =
                    productComponentFlux.toStream()
                            .map(ProductComponentEntity::from)
                            .collect(Collectors.toList());
            List<ProductComponentEntity> insertionList = removeDuplicates(productComponentEntityJpaRepository, productComponents);
            productComponentEntityJpaRepository.saveAll(insertionList);
            log.info("ProductComponentEntityDatabaseLoader initiated");
        };
    }

    private List<ProductComponentEntity> removeDuplicates(
            ProductComponentEntityJpaRepository productComponentEntityJpaRepository,
            List<ProductComponentEntity> productComponents)
    {
        List<ProductComponentEntity> productComponentEntitiesInDb = productComponentEntityJpaRepository.findAll();
        List<Integer> idsOfPresentProductComponentEntities =
                productComponentEntitiesInDb.stream()
                        .map(ProductComponentEntity::getId)
                        .collect(Collectors.toList());

       return productComponents.stream()
               .filter(p -> !idsOfPresentProductComponentEntities.contains(p.getId()))
               .collect(Collectors.toList());
    }
}
