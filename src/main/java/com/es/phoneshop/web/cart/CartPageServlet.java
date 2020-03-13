package com.es.phoneshop.web.cart;

import com.es.phoneshop.Error;
import com.es.phoneshop.exception.InvalidNumberException;
import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
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
        Map<Long, Error> errors = new HashMap<>();
        parameterMap.forEach((parameterName, parameterValues) -> {
            long productId = Integer.parseInt(parameterName);
            try {
                int quantity = Integer.parseInt(parameterValues[0]);
                if (quantity < 0) {
                    throw new InvalidNumberException();
                }
                CartService cartService = HttpSessionCartService.getInstance();
                Cart cart = cartService.getCart(request);
                cartService.updateCartItem(cart, productId, quantity);
            } catch (NumberFormatException e) {
                errors.put(productId, Error.NOT_A_NUMBER);
            } catch (NotEnoughStockException e) {
                errors.put(productId, Error.NOT_ENOUGH_STOCK);
            } catch (InvalidNumberException e) {
                errors.put(productId, Error.INVALID_NUMBER);
            }
        });
        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
