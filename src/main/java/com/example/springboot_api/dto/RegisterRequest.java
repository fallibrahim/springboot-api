package com.example.springboot_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email(message = "Email invalide")
    @NotBlank(message = "Email obligatoire")
    private String email;
    @NotBlank(message = "Mot de passe obligatoire")
    @Size(min = 4, message = "Mot de passe trop court")
    private String password;
    private String role;
}
