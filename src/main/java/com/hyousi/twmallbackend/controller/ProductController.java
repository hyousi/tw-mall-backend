package com.hyousi.twmallbackend.controller;

import com.hyousi.twmallbackend.domain.Product;
import com.hyousi.twmallbackend.entity.ProductEntity;
import com.hyousi.twmallbackend.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductEntity>> getProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        return ResponseEntity.ok(productEntityList);
    }

    @PostMapping("/api/products")
    public ResponseEntity addProduct(@RequestBody @Valid Product product) {
        Optional<ProductEntity> productEntity = productRepository.findByName(product.getName());
        if (productEntity.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            productRepository.save(product.toProductEntity());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }
}
