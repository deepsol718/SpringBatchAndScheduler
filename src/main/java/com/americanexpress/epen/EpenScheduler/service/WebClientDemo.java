package com.americanexpress.epen.EpenScheduler.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientDemo {

    private final WebClient.Builder webClientBuilder;

    public WebClientDemo(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<String> getUsersPage2() {
        WebClient webClient = webClientBuilder
                .baseUrl("https://reqres.in")
                .build();

        return webClient.get()
                .uri("/api/users?page=2")
                .retrieve()
                .bodyToMono(String.class);
    }
}
