package com.epam.training.jwd.online.shop.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The class defending from js injection
 * @author Olga Grigorieva
 * @version 1.0.0
 */

@WebFilter(filterName = "XssAttackFilter")
public class XssAttackFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new XssWrapper((HttpServletRequest) servletRequest), servletResponse);
    }

}
