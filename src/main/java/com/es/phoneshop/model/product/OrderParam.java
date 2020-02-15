package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum OrderParam {
    description(Comparator.comparing(Product::getDescription)),
    price(Comparator.comparing(Product::getPrice));

    private final Comparator<Product> comparator;

    OrderParam(Comparator<Product> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Product> getComparator() {
        return comparator;
    }

}
