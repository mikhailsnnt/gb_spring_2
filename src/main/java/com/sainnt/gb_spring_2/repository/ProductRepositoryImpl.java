package com.sainnt.gb_spring_2.repository;

import com.sainnt.gb_spring_2.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final Map<Long, Product> products;

    public ProductRepositoryImpl() {
        products = new ConcurrentHashMap<>();
        Stream.of(new Product(1, "Sample product", 1000),
                        new Product(2, "Other sample product", 2000),
                        new Product(3, "One more sample product", 40000))
                .forEach(p -> products.put(p.getId(), p));

    }

    @Override
    public List<Product> loadAll() {
        return List.copyOf(products.values());
    }

    @Override
    public Optional<Product> load(long id) {
        return Optional.ofNullable(products.get(id));
    }

}
