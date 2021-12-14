package com.vinsguru;

import com.vinsguru.dto.OperationResultResponse;
import com.vinsguru.model.Operation;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

public class Lec09AssignmentTest extends BaseTest {

    private static final String RESULT_PRINT_FORMAT = "%d %s %d = %s\n";

    @Test
    void assignmentTest() {
        final int firstOperand = 10;

        final Flux<OperationResultResponse> responseFlux = Flux.fromArray(Operation.values())
                .flatMap(operation -> Flux.range(1, 5)
                        .flatMap(send(firstOperand, operation.getOperator())));

        StepVerifier.create(responseFlux)
                .expectNextCount(20)
                .verifyComplete();
    }

    private Function<Integer, Mono<OperationResultResponse>> send(Integer firstOperand, String operator) {
        return secondOperand -> webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("calculator/{a}/{b}")
                        .build(firstOperand, secondOperand))
                .headers(httpHeaders -> httpHeaders.set("OP", operator))
                .retrieve()
                .bodyToMono(OperationResultResponse.class)
                .doOnNext(response ->
                        System.out.printf(RESULT_PRINT_FORMAT, firstOperand, operator, secondOperand, response.getResult()));
    }
}
