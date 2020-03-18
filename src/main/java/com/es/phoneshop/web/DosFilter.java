package com.es.phoneshop.web;

import com.es.phoneshop.model.dos.DosService;
import com.es.phoneshop.model.dos.DosServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*")
public class DosFilter implements Filter {

    private static final DosService dosService = DosServiceImpl.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String ip = request.getRemoteAddr();

        if (dosService.isAllowed(ip)) {
            filterChain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
