package com.vinsguru;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

class Lec07QueryParamsTest extends BaseTest {

    private static final String QUERY_STRING = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    void queryParamsTest() {
        final URI uri = UriComponentsBuilder.fromUriString(QUERY_STRING)
                .build(10, 20);

        final Map<String, Integer> map = Map.of(
                "count", 10,
                "page", 20
        );

        Flux<Integer> integerFlux = webClient
                .get()
//                .uri(uri)
                .uri(uriBuilder -> uriBuilder
                        .path("jobs/search")
                        .query("count={count}&page={page}")
//                        .build(10, 20))
                        .build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
