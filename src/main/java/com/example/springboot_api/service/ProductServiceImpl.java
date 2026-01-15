package com.example.springboot_api.service;

import com.example.springboot_api.Model.Product;
import com.example.springboot_api.dto.ProductRequestDto;
import com.example.springboot_api.dto.ProductResponseDto;
import com.example.springboot_api.exception.ResourceNotFoundException;
import com.example.springboot_api.mapper.ProductMapper;
import com.example.springboot_api.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
   private final ProductRepository productRepository;
   private final ProductMapper productMapper;

    @Override
    public ProductResponseDto save(ProductRequestDto dto) {
        Product product = productMapper.toEntity(dto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public Page<ProductResponseDto> getAll(int page, int size) {
//        return productRepository.findAll()
//                .stream()
//                .map(productMapper::toDto)
//                .toList();
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> getSorted(String field, String direction, int page, int size) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable).map(productMapper::toDto);
    }

    @Override
    public ProductResponseDto getById(long id) {
             Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit avec id " + id + " non trouvé"));
             return productMapper.toDto(product);
    }

    @Override
    public void deleteById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec id " + id + " non trouvé"));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto updateProduct(long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec id " + id + " non trouvé"));

        productMapper.updateEntity(dto, product);

        return productMapper.toDto(productRepository.save(product));
    }


}
