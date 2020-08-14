package com.hyousi.twmallbackend.repository;

import com.hyousi.twmallbackend.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    List<ProductEntity> findAll();

    Optional<ProductEntity> findByName(String name);
}
