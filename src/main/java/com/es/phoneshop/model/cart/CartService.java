package com.es.phoneshop.model.cart;

import java.util.List;

public class CartService {

    private final Cart cart = new Cart();

    public void add(CartItem addedCartItem) {
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProductId() == addedCartItem.getProductId()) {
                item.setQuantity(item.getQuantity() + addedCartItem.getQuantity());
                cart.setCartItems(cartItems);
                return;
            }
        }
        cartItems.add(addedCartItem);
        cart.setCartItems(cartItems);
    }

    public void add(Long productId, int quantity) {
        add(new CartItem(productId, quantity));
    }

    public Cart getCart() {
        return new Cart(cart);
    }
}
