package com.americanexpress.epen.EpenScheduler.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateDemo {

    private final RestTemplate restTemplate;

    public RestTemplateDemo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUsersPage2() {
        String url = "https://reqres.in/api/users?page=2";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
