package com.es.phoneshop.model.pricehistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListPriceHistoryDao implements PriceHistoryDao {

    private static final ArrayListPriceHistoryDao INSTANCE = new ArrayListPriceHistoryDao();

    private static List<HistoryRecord> history = new ArrayList<>();

    private static long nextId = 0;

    private ArrayListPriceHistoryDao() {}

    public static ArrayListPriceHistoryDao getInstance() {
        return INSTANCE;
    }

    @Override
    public HistoryRecord getRecord(Long id) {
        return Optional.of(history.stream()
                .filter(historyRecord -> historyRecord.getId() == id)
                .findAny()
                .get()).get();
    }

    @Override
    public List<HistoryRecord> getHistoryForProduct(Long productId) {
        return history.stream()
                .filter(historyRecord -> historyRecord.getProductId() == productId)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoryRecord> getFullHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void save(HistoryRecord historyRecord) {
        historyRecord.setId(nextId++);
        history.add(historyRecord);
    }

    @Override
    public void delete(Long id) {
        history.removeIf(historyRecord -> historyRecord.getId() == id);
    }
}
