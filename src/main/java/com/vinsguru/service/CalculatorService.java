package com.vinsguru.service;

import com.vinsguru.dto.OperationResultResponse;
import com.vinsguru.exception.InvalidOperatorValueException;
import com.vinsguru.model.Operation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CalculatorService {

    public Mono<OperationResultResponse> add(int firstOperand, int secondOperand) {
        return Mono.fromSupplier(() -> OperationResultResponse.builder()
                .operator(Operation.ADDITION.getOperator())
                .result(String.valueOf(firstOperand + secondOperand))
                .build());
    }

    public Mono<OperationResultResponse> subtract(int firstOperand, int secondOperand) {
        return Mono.fromSupplier(() -> OperationResultResponse.builder()
                .operator(Operation.SUBTRACTION.getOperator())
                .result(String.valueOf(firstOperand - secondOperand))
                .build());
    }

    public Mono<OperationResultResponse> multiply(int firstOperand, int secondOperand) {
        return Mono.fromSupplier(() -> OperationResultResponse.builder()
                .operator(Operation.MULTIPLICATION.getOperator())
                .result(String.valueOf(firstOperand * secondOperand))
                .build());
    }

    public Mono<OperationResultResponse> divide(int firstOperand, int secondOperand) {
        return Mono.just(secondOperand)
                .handle((value, sink) -> {
                    if (value == 0) {
                        sink.error(new InvalidOperatorValueException("Value of the second operand can't be " + value, String.valueOf(value)));
                    } else {
                        sink.next(OperationResultResponse.builder()
                                .operator(Operation.DIVISION.getOperator())
                                .result(String.valueOf(Integer.valueOf(firstOperand).doubleValue() / Integer.valueOf(secondOperand).doubleValue()))
                                .build());
                    }
                });
    }

}
