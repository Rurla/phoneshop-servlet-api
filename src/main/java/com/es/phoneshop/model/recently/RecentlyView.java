package com.es.phoneshop.model.recently;

import java.io.Serializable;
import java.util.LinkedList;

public class RecentlyView implements Serializable {

    private LinkedList<Long> productIds = new LinkedList<>();

    public LinkedList<Long> getProductIds() {
        return productIds;
    }

    public void setProductId(LinkedList<Long> productIds) {
        this.productIds = productIds;
    }
}
