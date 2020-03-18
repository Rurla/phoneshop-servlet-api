package com.es.phoneshop.model.order;

import java.util.List;

public interface OrderDao {

    Order getById(long id);

    List<Order> find();

    void delete(long id);

    void update(Order order);

    Long save(Order order);
}
