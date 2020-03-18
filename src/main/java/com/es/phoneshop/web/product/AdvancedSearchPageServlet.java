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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {

    private static final ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryString = request.getQueryString();
        List<Product> products = new ArrayList<>(0);
        Map<String, String> errors = new HashMap<>();
        setSearchParameters(request);
        String description = request.getParameter("description");
        String minPriceString = request.getParameter("minPrice");
        String maxPriceString = request.getParameter("maxPrice");
        String type = request.getParameter("type");
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        try {
            minPrice = parsePrice(minPriceString);
        } catch (NumberFormatException e) {
            errors.put("minPrice", "Not a number.");
        }
        try {
            maxPrice = parsePrice(maxPriceString);
        } catch (NumberFormatException e) {
            errors.put("maxPrice", "Not a number.");
        }
        if (errors.size() != 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/pages/productAdvancedSearch.jsp").forward(request, response);
            return;
        }

        if (queryString != null) {
            if (description.isEmpty() && minPriceString.isEmpty() && maxPriceString.isEmpty()) {
                products = productDao.findProducts();
            } else {
                if (type.equals("allWords")) {
                    products = productDao.findByQueryByAllWords(description, minPrice, maxPrice);
                } else {
                    products = productDao.findByQueryUnsorted(description, minPrice, maxPrice);
                }
            }
        }
        request.setAttribute("errors", errors);
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/pages/productAdvancedSearch.jsp").forward(request, response);
    }

    private BigDecimal parsePrice(String stringPrice) {
        BigDecimal price;
        if (stringPrice == null || stringPrice.isEmpty()) {
            price = new BigDecimal(0);
        } else {
            price = new BigDecimal(stringPrice);
        }
        return price;
    }

    private void setSearchParameters(HttpServletRequest request) {
        String description = request.getParameter("description");
        if (description == null) {
            description = "";
        }
        String type = request.getParameter("type");
        if (type == null) {
            type = "";
        }
        String minPriceString = request.getParameter("minPrice");
        if (minPriceString == null) {
            minPriceString = "";
        }
        String maxPriceString = request.getParameter("maxPrice");
        if (maxPriceString == null) {
            maxPriceString = "";
        }

        request.setAttribute("description", description);
        request.setAttribute("type", type);
        request.setAttribute("minPrice", minPriceString);
        request.setAttribute("maxPrice", maxPriceString);
    }
}
