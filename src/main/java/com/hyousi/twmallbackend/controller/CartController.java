package com.hyousi.twmallbackend.controller;

import com.hyousi.twmallbackend.domain.Product;
import com.hyousi.twmallbackend.entity.CartEntity;
import com.hyousi.twmallbackend.entity.ProductEntity;
import com.hyousi.twmallbackend.repository.CartRepository;
import com.hyousi.twmallbackend.repository.ProductRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class CartController {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/api/cart")
    public ResponseEntity addToCart(@RequestBody @Valid Product product) {
        Optional<ProductEntity> target = productRepository.findByName(product.getName());
        if (!target.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            ProductEntity productEntity = target.get();
            Optional<CartEntity> item = cartRepository.findByProductEntity_Name(productEntity.getName());
            CartEntity cartEntity = item
                .orElseGet(() -> CartEntity.builder().productEntity(productEntity).number(0).build());
            cartEntity.setNumber(cartEntity.getNumber() + 1);
            cartRepository.save(cartEntity);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
