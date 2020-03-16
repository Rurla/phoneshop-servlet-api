package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class DaoOrderService implements OrderService {

    private static final OrderDao ORDER_DAO = ArrayListOrderDao.getInstance();

    private static final ProductDao PRODUCT_DAO = ArrayListProductDao.getInstance();

    private static volatile DaoOrderService instance;

    private DaoOrderService() {}

    public static DaoOrderService getInstance() {
        if (instance == null) {
            synchronized (DaoOrderService.class) {
                if (instance == null) {
                    instance = new DaoOrderService();
                }
            }
        }
        return instance;
    }

    @Override
    public void placeOrder(Order order) {
        ORDER_DAO.save(order);
    }

    @Override
    public OrderItem cartItemToOrderItem(CartItem cartItem) {
        Product product = PRODUCT_DAO.getProduct(cartItem.getProductId());
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
