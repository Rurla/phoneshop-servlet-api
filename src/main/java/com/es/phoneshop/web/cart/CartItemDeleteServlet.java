package com.es.phoneshop.web.cart;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartService cartService = HttpSessionCartService.getInstance();
        Cart cart = cartService.getCart(request);
        long productId = Long.parseLong(request.getParameter("productId"));
        HttpSessionCartService.getInstance().removeCartItem(cart, productId);
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
