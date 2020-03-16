package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    void add(HttpServletRequest request, long productId, int quantity);

    Cart getCart(HttpServletRequest request);

    void removeCartItem(Cart cart, long productId);

    void updateCartItem(Cart cart, long productId, int quantity);

    void clearCart(HttpServletRequest request);

    void recalculateCart(Cart cart);

}
