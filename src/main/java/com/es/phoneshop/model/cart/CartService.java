package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface CartService {

    void add(HttpServletRequest request, long productId, int quantity);

    Optional<Cart> getCart(HttpServletRequest request);

}
