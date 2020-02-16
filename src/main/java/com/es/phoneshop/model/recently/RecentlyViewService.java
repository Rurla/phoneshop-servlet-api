package com.es.phoneshop.model.recently;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewService {

    void add(HttpServletRequest request, long productId);
}
