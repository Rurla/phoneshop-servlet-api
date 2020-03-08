package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);

    void updateProduct(Product product);

    List<Product> findByQuery(String query, OrderParam orderParam, Order order);
}
