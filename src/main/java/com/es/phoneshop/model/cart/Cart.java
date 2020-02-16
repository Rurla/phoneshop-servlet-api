package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {

    }

    public Cart(Cart cart) {
     cartItems = cart.cartItems;
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
