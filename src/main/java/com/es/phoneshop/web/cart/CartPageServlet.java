package com.es.phoneshop.web.cart;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CartPageServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartService cartService = HttpSessionCartService.getInstance();
        Cart cart = cartService.getCart(request);
        request.setAttribute("cartItems", cart.getCartItems());
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((parameterName, parameterValues) -> {
            long productId = Integer.parseInt(parameterName);
            int quantity = Integer.parseInt(parameterValues[0]);
            CartService cartService = HttpSessionCartService.getInstance();
            Cart cart = cartService.getCart(request);
            cartService.updateCartItem(cart, productId, quantity);
        });
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
