package com.hyousi.twmallbackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyousi.twmallbackend.entity.ProductEntity;
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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    private void setup() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        ProductEntity productA = ProductEntity.builder().name("可乐").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productB = ProductEntity.builder().name("雪碧").price(3).unit("瓶")
            .image("baidu.com").build();
        ProductEntity productC = ProductEntity.builder().name("芬达").price(3).unit("瓶")
            .image("baidu.com").build();
        productRepository.save(productA);
        productRepository.save(productB);
        productRepository.save(productC);

        mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].name", is("可乐")))
            .andExpect(jsonPath("$[1].name", is("雪碧")))
            .andExpect(jsonPath("$[2].name", is("芬达")))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldAddValidProduct() throws Exception {
        String productJson = "{ \"name\": \"百事可乐\", \"price\": 3, \"unit\": \"瓶\", \"image\": \"www.baidu.com\"}";

        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productJson))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldNotAddProduct() throws Exception {
        String productJson = "{ \"names\": \"百事可乐\", \"price\": null, \"unit\": \"瓶\", \"image\": \"www.baidu.com\"}";

        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productJson))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAddExistedProduct() throws Exception {
        ProductEntity productA = ProductEntity.builder().name("可乐").price(3).unit("瓶")
            .image("baidu.com").build();
        productRepository.save(productA);
        String productJson = objectMapper.writeValueAsString(productA.toProduct());

        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productJson))
            .andExpect(status().isBadRequest());
    }

}
