package com.example.springboot_api.controller;

import com.example.springboot_api.Model.Product;
import com.example.springboot_api.dto.ProductRequestDto;
import com.example.springboot_api.dto.ProductResponseDto;
import com.example.springboot_api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  // ➕ CREATE
  @PostMapping
  @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
  public ProductResponseDto create(
          @Valid @RequestBody ProductRequestDto dto) {
    return productService.save(dto);
  }

  // 📖 READ
  @GetMapping
  @PreAuthorize("hasAuthority('READ_PRODUCT')")
  public Page<ProductResponseDto> getAll(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size) {
    return productService.getAll(page, size);
  }

  @GetMapping("/sorted")
  @PreAuthorize("hasAuthority('READ_PRODUCT')")
  public Page<ProductResponseDto> getSorted(
          @RequestParam String field,
          @RequestParam(defaultValue = "asc") String direction,
          @RequestParam int page,
          @RequestParam int size) {
    return productService.getSorted(field, direction, page, size);
  }

  @GetMapping("/search")
  @PreAuthorize("hasAuthority('READ_PRODUCT')")
  public Page<ProductResponseDto> search(
          @RequestParam String keyword,
          @RequestParam int size,
          @RequestParam int page) {
    return productService.search(keyword, page, size);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('READ_PRODUCT')")
  public ProductResponseDto getById(@PathVariable Long id) {
    return productService.getById(id);
  }

  // ✏️ UPDATE
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
  public ProductResponseDto updateProduct(
          @PathVariable Long id,
          @Valid @RequestBody ProductRequestDto dto) {
    return productService.updateProduct(id, dto);
  }

  // ❌ DELETE
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
  public void delete(@PathVariable Long id) {
    productService.deleteById(id);
  }
}

