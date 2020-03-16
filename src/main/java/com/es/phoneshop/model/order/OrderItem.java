package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;

public class OrderItem extends CartItem {

    private BigDecimal price;

    public OrderItem(long productId, int quantity, BigDecimal price) {
        super(productId, quantity);
        this.price = price;
    }

    public OrderItem(CartItem cartItem, BigDecimal price) {
        this.setProductId(cartItem.getProductId());
        this.setQuantity(cartItem.getQuantity());
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
