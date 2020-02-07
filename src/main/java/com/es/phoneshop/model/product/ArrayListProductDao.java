package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static List<Product> products = new ArrayList<>();

    private static Long nextId = 0L;

    @Override
    public Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    public List<Product> findAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public synchronized void save(Product product) {
        if (product != null) {
            product.setId(nextId++);
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    @Override
    public List<Product> findByQuery(String query) {
        List<String> words = Arrays.asList(query.split(" "));
        ArrayList<Product> products = new ArrayList<>();
        words.forEach(word -> products.addAll(findProducts().stream()
                .filter(product -> product
                        .getDescription()
                        .toLowerCase()
                        .contains(word.toLowerCase()))
                .collect(Collectors.toList())));
        return products.stream()
                .distinct()
                .sorted((product, product1) -> {
                    AtomicInteger weight = new AtomicInteger(0);
                    words.forEach(word -> {
                        weight.updateAndGet(v -> v - (product.getDescription().contains(word) ? 1 : 0));
                        weight.updateAndGet(v -> v + (product1.getDescription().contains(word) ? 1 : 0));
                    });
                    return weight.get();
                }).collect(Collectors.toList());
    }
}
