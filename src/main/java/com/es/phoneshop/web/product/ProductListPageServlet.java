package com.es.phoneshop.web.product;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Order;
import com.es.phoneshop.model.product.OrderParam;
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
        String stringOrderParam = request.getParameter("orderParam");
        String stringOrder = request.getParameter("order");
        OrderParam orderParam = null;
        Order order = null;
        if (stringOrderParam != null || stringOrder != null) {
            orderParam = OrderParam.valueOf(stringOrderParam);
            order = Order.valueOf(stringOrder);
        }
        request.setAttribute("products", productDao.findByQuery(query, orderParam, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
