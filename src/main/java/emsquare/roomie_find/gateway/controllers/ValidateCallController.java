package emsquare.roomie_find.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateCallController {

    @GetMapping("/")
    public String home() {
        return "Application is running!";
    }
    
}
