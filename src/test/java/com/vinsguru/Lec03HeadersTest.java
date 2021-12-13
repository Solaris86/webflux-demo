package com.vinsguru;

import com.vinsguru.dto.MultiplyRequestDto;
import com.vinsguru.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03HeadersTest extends BaseTest {

    @Test
    void headersTest() {
        final Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .headers(headers -> headers.set("someKey", "someVal"))
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
