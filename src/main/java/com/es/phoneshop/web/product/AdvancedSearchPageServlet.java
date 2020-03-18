package com.es.phoneshop.web.product;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchPageServlet extends HttpServlet {

    private static final ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        String description = request.getParameter("description");
        String minPriceString = request.getParameter("minPrice");
        String maxPriceString = request.getParameter("maxPrice");
        List<Product> products = null;
        if (queryString != null) {
            if (description.isEmpty() && minPriceString.isEmpty() && maxPriceString.isEmpty()) {
                products = productDao.findProducts();
            }
        } else {
            products = new ArrayList<>();
        }
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/productAdvancedSearch.jsp").forward(request, response);
    }
}
