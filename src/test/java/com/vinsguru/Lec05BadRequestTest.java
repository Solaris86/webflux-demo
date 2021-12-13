package com.vinsguru;

import com.vinsguru.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05BadRequestTest extends BaseTest {

    @Test
    void badRequestTest() {
        final Mono<Response> responseMono = webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
