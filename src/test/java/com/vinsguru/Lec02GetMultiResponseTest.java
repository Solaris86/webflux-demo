package com.vinsguru;

import com.vinsguru.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Lec02GetMultiResponseTest extends BaseTest {

    @Test
    void fluxTest() {
        final Flux<Response> responseFlux = webClient
                .get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();

    }

    @Test
    void fluxStreamTest() {
        final Flux<Response> responseFlux = webClient
                .get()
                .uri("/reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
