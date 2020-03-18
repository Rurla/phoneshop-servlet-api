package com.es.phoneshop.web.product;

import com.es.phoneshop.model.pricehistory.ArrayListPriceHistoryDao;
import com.es.phoneshop.model.pricehistory.HistoryRecord;
import com.es.phoneshop.model.pricehistory.PriceHistoryDao;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PriceHistoryPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getPathInfo().replaceAll("/", ""));
        ProductDao productDao = ArrayListProductDao.getInstance();
        PriceHistoryDao priceHistoryDao = ArrayListPriceHistoryDao.getInstance();
        Product product = productDao.getProduct(productId);
        List<HistoryRecord> history = priceHistoryDao.getHistoryForProduct(productId);
        request.setAttribute("product", product);
        request.setAttribute("history", history);
        request.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(request, response);
    }
}
