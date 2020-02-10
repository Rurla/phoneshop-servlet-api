package com.es.phoneshop.model.product;

import com.es.phoneshop.model.pricehistory.ArrayListPriceHistoryDao;
import com.es.phoneshop.model.pricehistory.HistoryRecord;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListPriceHistoryDaoTest {

    private ArrayListPriceHistoryDao priceHistoryDao;

    @Before
    public void setup() {
        priceHistoryDao = ArrayListPriceHistoryDao.getInstance();
        List<HistoryRecord> history = new ArrayList<>(priceHistoryDao.getFullHistory());
        history.forEach(product -> priceHistoryDao.delete(product.getId()));
    }

    @Test
    public void testGetFullHistoryNoResults() {
        assertTrue(priceHistoryDao.getFullHistory().isEmpty());
    }

    @Test
    public void getHistoryForProduct() {
        priceHistoryDao.save(new HistoryRecord(0, new Date(1570665600000L), new BigDecimal(200)));
        assertFalse(priceHistoryDao.getHistoryForProduct(0L).isEmpty());
    }

    @Test
    public void saveNotNull() {
        priceHistoryDao.save(new HistoryRecord());
        assertFalse(priceHistoryDao.getFullHistory().isEmpty());
    }

    @Test
    public void saveNull() {
        priceHistoryDao.save((HistoryRecord) null);
        assertTrue(priceHistoryDao.getFullHistory().isEmpty());
    }

    @Test
    public void deleteFirst() {
        HistoryRecord record = new HistoryRecord();
        HistoryRecord record1 = new HistoryRecord();
        priceHistoryDao.save(record);
        priceHistoryDao.save(record1);
        priceHistoryDao.delete(priceHistoryDao.getFullHistory().get(0).getId());
        List<HistoryRecord> products = priceHistoryDao.getFullHistory();
        assertTrue(products.contains(record1) && !products.contains(record));
    }

    @Test
    public void deleteLast() {
        HistoryRecord record = new HistoryRecord();
        HistoryRecord record1 = new HistoryRecord();
        priceHistoryDao.save(record);
        priceHistoryDao.save(record1);
        priceHistoryDao.delete(priceHistoryDao.getFullHistory().get(1).getId());
        List<HistoryRecord> products = priceHistoryDao.getFullHistory();
        assertTrue(products.contains(record) && !products.contains(record1));
    }

    @Test
    public void getExistedRecord() {
        Optional<HistoryRecord> record = Optional.of(new HistoryRecord());
        priceHistoryDao.save(record);
        record = priceHistoryDao.getRecord(priceHistoryDao.getFullHistory().get(0).getId());
        assertNotNull(record);
    }

    @Test
    public void getNotExistedRecord() {
        try {
            priceHistoryDao.getRecord(0L);
        }
        catch(RuntimeException e) {
            assertEquals(e.getClass(), NoSuchElementException.class);
        }
    }
}
