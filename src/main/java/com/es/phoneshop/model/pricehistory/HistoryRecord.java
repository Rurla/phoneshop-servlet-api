package com.es.phoneshop.model.pricehistory;

import java.math.BigDecimal;
import java.util.Date;

public class HistoryRecord {

    private long id;

    private long productId;

    private Date date;

    private BigDecimal price;

    public HistoryRecord(long productId, Date date, BigDecimal price) {
        this.productId = productId;
        this.date = date;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
