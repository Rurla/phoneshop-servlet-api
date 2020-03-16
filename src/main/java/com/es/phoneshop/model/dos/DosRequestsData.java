package com.es.phoneshop.model.dos;

public class DosRequestsData {

    private String ip;
    private int numberOfRequests;

    public DosRequestsData(String ip, int numberOfRequests) {
        this.ip = ip;
        this.numberOfRequests = numberOfRequests;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }
}
