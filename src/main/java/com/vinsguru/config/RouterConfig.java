package com.vinsguru.config;

import com.vinsguru.dto.FailedInputValidationResponse;
import com.vinsguru.dto.InvalidOperationResponse;
import com.vinsguru.exception.InputValidationException;
import com.vinsguru.exception.InvalidOperatorValueException;
import com.vinsguru.handler.CalculatorHandler;
import com.vinsguru.handler.RequestHandler;
import com.vinsguru.model.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private static final String OPERATION_HEADER = "OP";

    private final RequestHandler requestHandler;
    private final CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .path("calculator", this::calculatorRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::squareHandler)
                .GET("square/{input}", req -> ServerResponse.ok().bodyValue("only 10 - 19 allowed"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private RouterFunction<ServerResponse> calculatorRouterFunction() {
        return RouterFunctions.route()
                .GET("{firstOperand}/{secondOperand}",
                        isOperation(Operation.ADDITION.getOperator()),
                        calculatorHandler::handleAddition)
                .GET("{firstOperand}/{secondOperand}",
                        isOperation(Operation.SUBTRACTION.getOperator()),
                        calculatorHandler::handleSubtraction)
                .GET("{firstOperand}/{secondOperand}",
                        isOperation(Operation.MULTIPLICATION.getOperator()),
                        calculatorHandler::handleMultiplication)
                .GET("{firstOperand}/{secondOperand}",
                        RequestPredicates.headers(headers -> Operation.DIVISION.getOperator().equals(headers.firstHeader(OPERATION_HEADER))),
                        calculatorHandler::handleDivision)
                .GET("{firstOperand}/{secondOperand}",
                        req -> ServerResponse.badRequest().bodyValue(InvalidOperationResponse.builder()
                                        .message(req.headers().firstHeader(OPERATION_HEADER) + " is invalid operator.")
                                .build()))
                .onError(InvalidOperatorValueException.class, (err, serverRequest) -> ServerResponse.badRequest().bodyValue(err.getMessage()))
                .build();
    }

    private RequestPredicate isOperation(String operator) {
        return RequestPredicates.headers(headers -> operator.equals(headers.firstHeader(OPERATION_HEADER)));
    }

    private BiFunction<InputValidationException, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, request) -> ServerResponse.badRequest()
                  .bodyValue(FailedInputValidationResponse.builder()
                          .input(err.getInput())
                          .message(err.getMessage())
                          .errorCode(err.getErrorCode())
                          .build());
    }
}
