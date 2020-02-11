package com.es.phoneshop.model.pricehistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ArrayListPriceHistoryDao implements PriceHistoryDao {

    private static final ArrayListPriceHistoryDao INSTANCE = new ArrayListPriceHistoryDao();

    private static List<HistoryRecord> history = new ArrayList<>();

    private static AtomicLong nextId = new AtomicLong(0);

    private ArrayListPriceHistoryDao() {
    }

    public static ArrayListPriceHistoryDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<HistoryRecord> getRecord(Long id) {
        return Optional.of(
                history.stream()
                        .filter(historyRecord -> historyRecord.getId() == id)
                        .findAny()
                        .get()
        );
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
        save(Optional.ofNullable(historyRecord));
    }

    public void save(Optional<HistoryRecord> historyRecord) {
        historyRecord.ifPresent(record -> {
            record.setId(nextId.getAndIncrement());
            history.add(record);
        });
    }

    @Override
    public void delete(Long id) {
        history.removeIf(historyRecord -> historyRecord.getId() == id);
    }
}
