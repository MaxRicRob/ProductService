package com.example.ProductServiceApplication.domain.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class ExternalApiTest {

    @Test
    void getRandomUUID() {

        UUID uuid = ExternalApi.getRandomUUID();

        assertThat(uuid).isNotNull();

    }
}
