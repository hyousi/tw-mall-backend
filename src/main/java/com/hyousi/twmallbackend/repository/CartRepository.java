package com.hyousi.twmallbackend.repository;

import com.hyousi.twmallbackend.entity.CartEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepository extends CrudRepository<CartEntity, Integer> {

    List<CartEntity> findAll();

    @Query("FROM CartEntity cart where cart.productEntity.name = :name")
    Optional<CartEntity> findByProductEntity_Name(@Param("name") String name);

    @Transactional
    @Query("DELETE FROM CartEntity cart where cart.productEntity.name = :name")
    void deleteByProductEntity_Name(@Param("name") String name);
}