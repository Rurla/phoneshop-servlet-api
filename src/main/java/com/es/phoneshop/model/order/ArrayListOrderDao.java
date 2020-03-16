package com.es.phoneshop.model.order;

import com.es.phoneshop.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {

    private static final List<Order> orders = new ArrayList<>();

    private static int nextId = 0;


    private static volatile ArrayListOrderDao instance;

    private ArrayListOrderDao() {}

    public static ArrayListOrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Order getById(long id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> find() {
        return new ArrayList<>(orders);
    }

    @Override
    public void delete(long id) {
        orders.removeIf(order -> order.getId() == id);
    }

    @Override
    public synchronized void update(Order order) {
        orders.stream()
                .filter(order1 -> order.getId() == order1.getId())
                .findAny()
                .ifPresent(order1 -> {
                    order1.setItems(order.getItems());
                    order1.setTotalPrice(order.getTotalPrice());
                    order1.setFirstName(order.getFirstName());
                    order1.setLastName(order.getLastName());
                    order1.setPhoneNumber(order.getPhoneNumber());
                    order1.setDeliveryAddress(order.getDeliveryAddress());
                    order1.setDeliveryDate(order.getDeliveryDate());
                    order1.setDeliveryCosts(order.getDeliveryCosts());
                    order1.setPaymentMethod(order.getPaymentMethod());
                });
    }

    @Override
    public synchronized void save(Order order) {
        if (order != null) {
            order.setId(nextId++);
            orders.add(order);
        }
    }
}
