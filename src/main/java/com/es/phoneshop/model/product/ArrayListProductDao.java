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

    private ArrayListProductDao() {}

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
    public List<Product> findByQuery(String query, String orderParam, String order) {
        List<String> words = Arrays.asList(query.split(" "));
        List<Product> products = findByQueryUnsorted(query);
        if (order == null || orderParam == null) {
            return defaultSort(products, words);
        }
        List<Product> filteredProducts = products.stream().distinct().collect(Collectors.toList());
        return sort(filteredProducts, orderParam, order);
    }

    public List<Product> findByQueryUnsorted(String query) {
        List<String> words = Arrays.asList(query.split(" "));
        ArrayList<Product> products = new ArrayList<>();
        words.forEach(word -> products.addAll(findProducts().stream()
                .filter(product -> product
                        .getDescription()
                        .toLowerCase()
                        .contains(word.toLowerCase()))
                .collect(Collectors.toList())));
        return products;
    }

    private static List<Product> sort(List<Product> products, String orderParam, String order) {
        products.sort((product, product1) -> {
            if (orderParam.equals("description")) {
                if (order.equals("asc")) {
                    return product.getDescription().compareTo(product1.getDescription());
                }
                if (order.equals("desc")) {
                    return product1.getDescription().compareTo(product.getDescription());
                }
                return 0;
            }
            if (orderParam.equals("price")) {
                if (order.equals("asc")) {
                    return product.getPrice().compareTo(product1.getPrice());
                }
                if (order.equals("desc")) {
                    return product1.getPrice().compareTo(product.getPrice());
                }
                return 0;
            }
            return 0;
        });
        return products;
    }

    private static List<Product> defaultSort(List<Product> products, List<String> queryWords) {
        return products.stream()
                .distinct()
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
