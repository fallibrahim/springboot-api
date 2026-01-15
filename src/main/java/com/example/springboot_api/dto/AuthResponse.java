package com.example.springboot_api.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}

