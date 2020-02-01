package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        List<Product> products = new ArrayList<>(((ArrayListProductDao) productDao).findAllProducts());
        products.forEach(product -> productDao.delete(product.getId()));
    }

    @Test
    public void findNullPriceProducts() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", null, Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void findZeroStockProducts() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void findNullPriceAndZeroStockProducts() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", null, Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void findProducts() {
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void findAllProducts() {
        Product product1 = new Product("sgs", "Samsung Galaxy S", null, Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product1);
        Product product2 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product2);
        Product product3 = new Product("sgs", "Samsung Galaxy S", null, Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product3);
        Product product4 = new Product("s", "S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubuseosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product4);
        List<Product> productList = new ArrayList<>(4);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        assertEquals(((ArrayListProductDao) productDao).findAllProducts(), productList);
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void saveNotNull() {
        productDao.save(new Product());
        assertFalse(((ArrayListProductDao)productDao).findAllProducts().isEmpty());
    }

    @Test
    public void saveNull() {
        productDao.save(null);
        assertTrue(((ArrayListProductDao)productDao).findAllProducts().isEmpty());
    }

    @Test
    public void delete() {
        Product product = new Product();
        productDao.save(product);
        productDao.delete(product.getId());
        assertTrue(((ArrayListProductDao)productDao).findAllProducts().isEmpty());
    }

    @Test
    public void getExistedProduct() {
        Product product = new Product();
        productDao.save(product);
        product = productDao.getProduct(product.getId());
        assertNotNull(product);
    }

    @Test
    public void getNotExistedProduct() {
        try {
            productDao.getProduct(0L);
        }
        catch(RuntimeException e) {
            assertEquals(e.getClass(), ProductNotFoundException.class);
        }
    }
}
