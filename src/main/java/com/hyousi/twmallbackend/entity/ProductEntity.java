package com.hyousi.twmallbackend.entity;

import com.hyousi.twmallbackend.domain.Product;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private float price;

    private String unit;

    private String image;

    public Product toProduct() {
        return new Product(name, price, unit, image);
    }
}