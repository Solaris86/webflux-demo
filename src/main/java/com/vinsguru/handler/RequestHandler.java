package com.vinsguru.handler;

import com.vinsguru.dto.MultiplyRequestDto;
import com.vinsguru.dto.Response;
import com.vinsguru.exception.InputValidationException;
import com.vinsguru.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));
        final Mono<Response> response = mathService.findSquare(input);

        return ServerResponse.ok().body(response, Response.class);
    }


    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));

        return ServerResponse.ok().body(mathService.multiplicationTable(input), Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(mathService.multiplicationTable(input), Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        final Mono<MultiplyRequestDto> requestDtoMono = serverRequest
                .bodyToMono(MultiplyRequestDto.class)
                .doOnNext(multiplyRequestDto -> System.out.println(serverRequest.headers()));
        final Mono<Response> responseMono = mathService.multiply(requestDtoMono);

        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        return ServerResponse.ok().body(mathService.findSquare(input), Response.class);
    }

}
