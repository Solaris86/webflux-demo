package com.vinsguru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultHeaders(headers -> headers.setBasicAuth("username", "password"))
                .filter(this::sessionToken)
                .build();
    }

//    private Mono<ClientResponse> sessionToken(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
//        System.out.println("Generating session token");
//        ClientRequest request = ClientRequest.from(clientRequest)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-lengthy-jwt"))
//                .build();
//
//        return exchangeFunction.exchange(request);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        ClientRequest request = clientRequest.attribute("auth")
                .map(attr -> "basic".equals(attr) ? withBasicAuth(clientRequest) : withOAuth(clientRequest))
                .orElse(clientRequest);

        return exchangeFunction.exchange(request);
    }

    private ClientRequest withBasicAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-token"))
                .build();
    }

}
