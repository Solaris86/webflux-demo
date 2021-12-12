package com.vinsguru.handler;

import com.vinsguru.dto.OperationResultResponse;
import com.vinsguru.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CalculatorHandler {

    private static final String FIRST_OPERAND_PATH_VARIABLE = "firstOperand";
    private static final String SECOND_OPERAND_PATH_VARIABLE = "secondOperand";

    private final CalculatorService calculatorService;

    public Mono<ServerResponse> handleAddition(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(processOperation(serverRequest, calculatorService::add),
                        OperationResultResponse.class);
    }

    public Mono<ServerResponse> handleSubtraction(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(processOperation(serverRequest, calculatorService::subtract),
                OperationResultResponse.class);
    }

    public Mono<ServerResponse> handleMultiplication(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(processOperation(serverRequest, calculatorService::multiply),
                        OperationResultResponse.class);
    }

    public Mono<ServerResponse> handleDivision(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(processOperation(serverRequest, calculatorService::divide),
                        OperationResultResponse.class);
    }

    private Mono<OperationResultResponse> processOperation(ServerRequest serverRequest, BiFunction<Integer, Integer, Mono<OperationResultResponse>> opLogic) {
        final int firstOperand = getValue(serverRequest, FIRST_OPERAND_PATH_VARIABLE);
        final int secondOperand = getValue(serverRequest, SECOND_OPERAND_PATH_VARIABLE);

        return opLogic.apply(firstOperand, secondOperand);
    }

    private int getValue(ServerRequest serverRequest, String key) {
        return Integer.parseInt(serverRequest.pathVariable(key));
    }

}
