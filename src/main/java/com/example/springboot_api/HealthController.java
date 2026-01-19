package com.example.springboot_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "status", "UP",
                "service", "springboot-api",
                "swagger", "/swagger-ui/index.html"
        );
    }
}
