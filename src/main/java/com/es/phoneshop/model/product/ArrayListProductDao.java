package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();

    private static List<Product> products = new ArrayList<>();

    private static Long nextId = 0L;

    public static ArrayListProductDao getInstance() {
        return INSTANCE;
    }

    private ArrayListProductDao() {
    }

    @Override
    public Product getProduct(Long id) {
        return new Product(products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id)));
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
    public void updateProduct(Product product) {
        if (product != null) {
            products.forEach(product1 -> {
                if (product.getId().equals(product1.getId())) {
                    product1.setAvailable(product.getAvailable());
                    product1.setStock(product.getStock());
                    product1.setCode(product.getCode());
                    product1.setCurrency(product.getCurrency());
                    product1.setImageUrl(product.getImageUrl());
                    product1.setDescription(product.getDescription());
                    product1.setPrice(product.getPrice());
                }
            });
        }
    }

    @Override
    public List<Product> findByQuery(String query, OrderParam orderParam, Order order) {
        List<String> words = Arrays.asList(query.split(" "));
        List<Product> products = findByQueryUnsorted(query);
        if (order == null || orderParam == null) {
            return defaultSort(products, words);
        }
        return sort(products, orderParam, order);
    }

    public List<Product> findByQueryUnsorted(String query) {
        List<String> words = Arrays.asList(query.split(" "));
        ArrayList<Product> products = new ArrayList<>();
        List<Product> allProducts = findProducts();
        words.forEach(word -> products.addAll(allProducts.stream()
                .filter(product -> product
                        .getDescription()
                        .toLowerCase()
                        .contains(word.toLowerCase()))
                .collect(Collectors.toList())));
        return products.stream().distinct()
                .collect(Collectors.toList());
    }

    private static List<Product> sort(List<Product> products, OrderParam orderParam, Order order) {
        Comparator<Product> comparator = orderParam.getComparator();
        if (order == Order.asc) {
            products.sort(comparator);
        } else if (order == Order.desc) {
            products.sort(comparator.reversed());
        }
        return products;
    }

    private static List<Product> defaultSort(List<Product> products, List<String> queryWords) {
        return products.stream()
                .sorted((product, product1) -> {
                    AtomicInteger weight = new AtomicInteger(0);
                    queryWords.forEach(word -> {
                        weight.updateAndGet(v -> v - (product.getDescription().contains(word) ? 1 : 0));
                        weight.updateAndGet(v -> v + (product1.getDescription().contains(word) ? 1 : 0));
                    });
                    return weight.get();
                }).collect(Collectors.toList());
    }


}
