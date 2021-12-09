package com.vinsguru.controller;

import com.vinsguru.dto.Response;
import com.vinsguru.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {

    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

}
