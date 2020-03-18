package com.es.phoneshop.web.product;

import com.es.phoneshop.exception.InvalidNumberException;
import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recently.HttpSessionRecentlyViewService;
import com.es.phoneshop.model.recently.RecentlyViewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = (String) request.getAttribute("message");
        if (message == null) {
            message = "";
        }
        boolean success = Boolean.parseBoolean(request.getParameter("success"));
        if (success) {
            message = "Added to cart successfully.";
        }
        long productId = Long.parseLong(request.getPathInfo().replaceAll("/", ""));
        RecentlyViewService recentlyViewService = HttpSessionRecentlyViewService.getInstance();
        recentlyViewService.add(request, productId);
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        request.setAttribute("message", message);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartService cartService = HttpSessionCartService.getInstance();
        long productId = Long.parseLong(request.getPathInfo().replaceAll("/", ""));
        String stringQuantity = request.getParameter("quantity");
        int quantity;
        try {
            Locale locale = request.getLocale();
            quantity = NumberFormat.getNumberInstance(locale).parse(stringQuantity).intValue();
            if (quantity < 1) {
                throw new InvalidNumberException();
            }
            cartService.add(request, productId, quantity);
        } catch (ParseException e) {
            String message = String.format("\"%s\" not a number.", stringQuantity);
            sendExceptionMessage(message, request, response);
            return;
        } catch (NotEnoughStockException | InvalidNumberException e) {
            sendExceptionMessage(e.getMessage(), request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private void sendExceptionMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        doGet(request, response);
    }
}
