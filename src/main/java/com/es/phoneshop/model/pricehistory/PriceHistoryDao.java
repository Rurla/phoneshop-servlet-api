package com.es.phoneshop.model.pricehistory;

import com.es.phoneshop.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryDao {

    Optional<HistoryRecord> getRecord(Long id);

    List<HistoryRecord> getHistoryForProduct(Long productId);

    List<HistoryRecord> getFullHistory();

    void save(HistoryRecord historyRecord);

    void delete(Long id);
}
