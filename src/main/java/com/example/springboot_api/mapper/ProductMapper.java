package com.example.springboot_api.mapper;

import com.example.springboot_api.Model.Product;
import com.example.springboot_api.dto.ProductRequestDto;
import com.example.springboot_api.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return product;
    }

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public void updateEntity(ProductRequestDto dto, Product product) {
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
    }
}
