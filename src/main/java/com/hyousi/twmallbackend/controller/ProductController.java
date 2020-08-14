package com.hyousi.twmallbackend.controller;

import com.hyousi.twmallbackend.entity.ProductEntity;
import com.hyousi.twmallbackend.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductEntity>> getProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        return ResponseEntity.ok(productEntityList);
    }
}
