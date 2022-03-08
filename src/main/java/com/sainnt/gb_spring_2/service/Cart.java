package com.sainnt.gb_spring_2.service;

import com.sainnt.gb_spring_2.entity.Product;
import com.sainnt.gb_spring_2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Cart {
    private final ProductRepository repository;
    private final Map<Long, Product> products = new HashMap<>();

    @Autowired
    public Cart(ProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(long id) {
        repository.load(id).ifPresentOrElse(product -> products.put(id, product),
                () -> System.out.printf("Product with id %d not found!", id));
    }

    public void removeProduct(long id) {
        if (products.containsKey(id))
            products.remove(id);
        else
            System.out.printf("Product with id %d not found!",id);
    }

    public Iterable<Product> getContent(){
        return products.values();
    }

}
