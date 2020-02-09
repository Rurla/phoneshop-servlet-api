package com.es.phoneshop.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductNotFoundServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("exception", request.getAttribute("javax.servlet.error.exception"));
        request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
    }
}
