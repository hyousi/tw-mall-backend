package com.hyousi.twmallbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyousi.twmallbackend.entity.CartEntity;
import com.hyousi.twmallbackend.entity.ProductEntity;
import com.hyousi.twmallbackend.repository.CartRepository;
import com.hyousi.twmallbackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void setup() {
        cartRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void shouldAddToCart() throws Exception {
        ProductEntity productA = ProductEntity.builder().name("可乐").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productB = ProductEntity.builder().name("雪碧").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productC = ProductEntity.builder().name("芬达").price(3).unit("瓶")
            .image("baidu.com").build();
        productRepository.save(productA);
        productRepository.save(productB);
        productRepository.save(productC);

        String requestJson = objectMapper.writeValueAsString(productC);

        mockMvc.perform(post("/api/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk());
        assertEquals(1, cartRepository.findAll().size());
        assertEquals(1, cartRepository.findByProductEntity_Name("芬达").get().getNumber());
    }

    @Test
    public void shouldUpdateCart() throws Exception {
        ProductEntity productA = ProductEntity.builder().name("可乐").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productB = ProductEntity.builder().name("雪碧").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productC = ProductEntity.builder().name("芬达").price(3).unit("瓶")
            .image("baidu.com").build();
        productRepository.save(productA);
        productRepository.save(productB);
        productRepository.save(productC);

        String requestJson = objectMapper.writeValueAsString(productC.toProduct());

        mockMvc.perform(post("/api/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk());

        assertEquals(1, cartRepository.findAll().size());
        assertEquals(2, cartRepository.findByProductEntity_Name("芬达").get().getNumber());
    }

    @Test
    public void shouldRemoveFromCart() throws Exception {
        ProductEntity productA = ProductEntity.builder().name("可乐").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productB = ProductEntity.builder().name("雪碧").price(3).unit("瓶")
            .image("baidu.com").build();
        productRepository.save(productA);
        productRepository.save(productB);
        cartRepository.save(CartEntity.builder().productEntity(productA).number(0).build());

        String requestJson = objectMapper.writeValueAsString(productA.toProduct());

        mockMvc.perform(delete("/api/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk());

        assertEquals( 0,  cartRepository.count());
    }
}
