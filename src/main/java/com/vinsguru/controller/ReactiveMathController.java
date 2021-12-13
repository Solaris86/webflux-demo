package com.vinsguru.controller;

import com.vinsguru.dto.MultiplyRequestDto;
import com.vinsguru.dto.Response;
import com.vinsguru.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {

    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }

    @GetMapping(value = "/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                   @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return this.mathService.multiply(requestDtoMono);
    }

}
