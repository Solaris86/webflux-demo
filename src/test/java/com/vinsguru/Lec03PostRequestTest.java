package com.vinsguru;

import com.vinsguru.dto.MultiplyRequestDto;
import com.vinsguru.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03PostRequestTest extends BaseTest {

    @Test
    void postTest() {
        final Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        final MultiplyRequestDto multiplyRequestDto = new MultiplyRequestDto();
        multiplyRequestDto.setFirst(a);
        multiplyRequestDto.setSecond(b);

        return multiplyRequestDto;
    }
}
