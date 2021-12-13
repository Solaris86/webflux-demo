package com.vinsguru;

import com.vinsguru.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class Lec01GetSingleResponseTest extends BaseTest {

    @Test
    void blockTest() {
        Response response = this.webClient
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response );
    }

    @Test
    void stepVerifiedTest() {
        final Mono<Response> responseMono = this.webClient
                .get()
                .uri("/reactive-math/square/{input}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getOutput() == 25)
                .verifyComplete();
    }

}
