package com.sainnt.gb_spring_2.repository;

import com.sainnt.gb_spring_2.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> loadAll();

    Optional<Product> load(long id);
}
