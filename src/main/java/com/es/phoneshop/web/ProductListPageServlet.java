package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        String query = request.getParameterMap().getOrDefault("query", new String[]{""})[0];
        String orderParam = request.getParameterMap().getOrDefault("orderParam", new String[]{""})[0];
        String order = request.getParameterMap().getOrDefault("order", new String[]{""})[0];
        request.setAttribute("products", productDao.findByQuery(query, orderParam, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
