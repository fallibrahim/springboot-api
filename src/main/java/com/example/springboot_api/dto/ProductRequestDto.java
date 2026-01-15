package com.example.springboot_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @Min(value = 1, message = "Le prix doit etre positif")
    private double price;
}