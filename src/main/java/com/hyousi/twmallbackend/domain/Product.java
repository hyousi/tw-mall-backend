package com.hyousi.twmallbackend.domain;

import com.hyousi.twmallbackend.entity.ProductEntity;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @NotNull
    private String name;

    @NotNull
    private float price;

    @NotNull
    private String unit;

    private String image;

    public ProductEntity toProductEntity() {
        return ProductEntity.builder()
            .name(name)
            .price(price)
            .unit(unit)
            .image(image)
            .build();
    }

}
