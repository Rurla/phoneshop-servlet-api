package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    private ArrayListProductDao productDao;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup(){
        productDao = ArrayListProductDao.getInstance();
        List<Product> products = new ArrayList<>(productDao.findAllProducts());
        products.forEach(product -> productDao.delete(product.getId()));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        productDao.save(new Product());
        long productId = productDao.findAllProducts().get(0).getId();
        when(request.getPathInfo()).thenReturn("/" + productId);
        servlet.doGet(request, response);

        verify(request).setAttribute("product", productDao.getProduct(productId));
        verify(requestDispatcher).forward(request, response);
    }
}
