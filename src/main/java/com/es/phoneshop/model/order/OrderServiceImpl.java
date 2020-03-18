package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final OrderDao orderDao = ArrayListOrderDao.getInstance();

    private static final ProductDao productDao = ArrayListProductDao.getInstance();

    private static volatile OrderServiceImpl instance;

    private OrderServiceImpl() {}

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public long placeOrder(Order order) {
        return orderDao.save(order);

    }

    @Override
    public OrderItem cartItemToOrderItem(CartItem cartItem) {
        Product product = productDao.getProduct(cartItem.getProductId());
        return new OrderItem(cartItem, product.getPrice());
    }

    @Override
    public List<CartItem> cartItemsToOrderItems(List<CartItem> cartItems) {
        List<CartItem> orderItems = new ArrayList<>(cartItems.size());
        cartItems.forEach(cartItem -> {
            OrderItem orderItem = cartItemToOrderItem(cartItem);
            orderItems.add(orderItem);
        });
        return orderItems;
    }

    @Override
    public Order cartToOrder(Cart cart) {
        Order order = new Order();
        List<CartItem> orderItems = cartItemsToOrderItems(cart.getItems());
        order.setItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());
        return order;
    }
}
