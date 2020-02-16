package com.es.phoneshop.model.recently;

import javax.servlet.http.HttpServletRequest;

public class HttpSessionRecentlyViewService implements RecentlyViewService {

    private static volatile RecentlyViewService recentlyViewService;

    public static RecentlyViewService getInstance() {
        if (recentlyViewService == null) {
            synchronized (HttpSessionRecentlyViewService.class) {
                if (recentlyViewService == null) {
                    recentlyViewService = new HttpSessionRecentlyViewService();
                }
            }
        }
        return recentlyViewService;
    }

    @Override
    public void add(HttpServletRequest request, long productId) {
        RecentlyView recentlyView = (RecentlyView)request.getSession().getAttribute("recently");
        if (recentlyView == null) {
            recentlyView = new RecentlyView();
        }
        recentlyView.getProductIds().remove(productId);
        if (recentlyView.getProductIds().size() == 3) {
            recentlyView.getProductIds().removeLast();
        }
        recentlyView.getProductIds().addFirst(productId);
        request.getSession().setAttribute("recently", recentlyView);
    }
}
