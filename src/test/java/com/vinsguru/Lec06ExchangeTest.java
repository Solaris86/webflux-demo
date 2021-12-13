package com.vinsguru;

import com.vinsguru.dto.FailedInputValidationResponse;
import com.vinsguru.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

    @Test
    void exchangeTest() {
        final Mono<Object> responseMono = webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        return clientResponse.rawStatusCode() == 400 ? clientResponse.bodyToMono(FailedInputValidationResponse.class)
                : clientResponse.bodyToMono(Response.class);
    }
}
