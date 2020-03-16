package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.util.List;

public interface OrderService {

    long placeOrder(Order order);

    OrderItem cartItemToOrderItem(CartItem cartItem);

    List<CartItem> cartItemsToOrderItems(List<CartItem> cartItems);

    Order cartToOrder(Cart cart);

}
