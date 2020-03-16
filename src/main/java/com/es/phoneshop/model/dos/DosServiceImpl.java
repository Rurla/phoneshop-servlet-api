package com.es.phoneshop.model.dos;

import java.util.HashMap;
import java.util.Map;

public class DosServiceImpl implements DosService {

    private static volatile DosServiceImpl instance;

    private final Map<String, Integer> numberOfRequestsByIp;
    private final int REQUESTS_LIMIT = 20;
    private final long TIME_LIMIT = 60000;
    private long refreshTime;

    public static DosServiceImpl getInstance() {
        if (instance == null) {
            synchronized (DosServiceImpl.class) {
                instance = new DosServiceImpl();
            }
        }
        return instance;
    }

    private DosServiceImpl() {
        numberOfRequestsByIp = new HashMap<>();
        refreshTime = System.currentTimeMillis();
    }

    @Override
    public boolean isAllowed(String ip) {
        refresh();

        Integer numberOfRequests = numberOfRequestsByIp.getOrDefault(ip, 0);

        numberOfRequests++;
        numberOfRequestsByIp.put(ip, numberOfRequests);
        return  numberOfRequests < REQUESTS_LIMIT;
    }

    private void refresh() {
        long currentTime = System.currentTimeMillis();

        if(currentTime - refreshTime > TIME_LIMIT) {
            refreshTime = currentTime;
            numberOfRequestsByIp.clear();
        }
    }
}
