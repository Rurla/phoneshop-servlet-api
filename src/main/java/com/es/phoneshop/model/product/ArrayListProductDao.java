package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayList<Product> products = new ArrayList<Product>();

    @Override
    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny();
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new RuntimeException("Product not found");
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    public List<Product> findAllProducts() {
        return products;
    }

    @Override
    public synchronized void save(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .ifPresent(product -> products.remove(product));
    }
}
