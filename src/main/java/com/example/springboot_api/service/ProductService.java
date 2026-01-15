package com.example.springboot_api.service;

import com.example.springboot_api.Model.Product;
import com.example.springboot_api.dto.ProductRequestDto;
import com.example.springboot_api.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    ProductResponseDto save(ProductRequestDto dto);
    Page<ProductResponseDto> getAll(int page, int size);
    Page<ProductResponseDto> getSorted(String field, String direction, int page, int size);
    Page<ProductResponseDto> search(String keyword, int page, int size);
    ProductResponseDto getById(long id);
    void deleteById(long id);
    ProductResponseDto updateProduct(long id, ProductRequestDto dto);

}
