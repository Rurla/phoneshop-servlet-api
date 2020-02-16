package com.es.phoneshop.model.recently;

import java.util.LinkedList;
import java.util.List;

public class RecentlyView {

    private LinkedList<Long> productIds = new LinkedList<>();

    public LinkedList<Long> getProductIds() {
        return productIds;
    }

    public void setProductId(LinkedList<Long> productIds) {
        this.productIds = productIds;
    }
}
