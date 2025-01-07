package emsquare.roomie_find.gateway.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import emsquare.roomie_find.gateway.dtos.LoginRequest;
import emsquare.roomie_find.gateway.dtos.LoginResponse;

@RestController
@RequestMapping("/gateway")
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String pamUrl = UriComponentsBuilder.fromUriString(pamServiceUrl)
                .path("/login")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<LoginRequest> pamEntity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> pamResponse = restTemplate.exchange(pamUrl, HttpMethod.POST, pamEntity, LoginResponse.class);

        try {
            return ResponseEntity.status(pamResponse.getStatusCode())
                .body(pamResponse.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new LoginResponse(null));
        }

    }

    
}
