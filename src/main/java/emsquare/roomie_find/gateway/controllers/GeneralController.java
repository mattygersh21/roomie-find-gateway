package emsquare.roomie_find.gateway.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import emsquare.roomie_find.gateway.dtos.LoginRequest;

@RestController
@RequestMapping("/gateway")
@CrossOrigin(origins = "*")
public class GeneralController {

    @Value("${pam.service.url}")
    private String pamServiceUrl;

    private final RestTemplate restTemplate;

    public GeneralController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String home() {
        return "Application is running!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String pamUrl = UriComponentsBuilder.fromUriString(pamServiceUrl)
                .path("/login")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");

        HttpEntity<LoginRequest> pamEntity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<String> pamResponse = restTemplate.exchange(
                pamUrl, HttpMethod.POST, pamEntity, String.class);

        // TODO: Add proper logging
        System.out.println("PAM Response: " + pamResponse.getBody());

        // Forward status and body as-is
        return ResponseEntity.status(pamResponse.getStatusCode())
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(pamResponse.getBody());
    }
}