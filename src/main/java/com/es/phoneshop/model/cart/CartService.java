package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    void add(HttpServletRequest request, long productId, int quantity);

}
