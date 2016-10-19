package com.aristotle.admin.filter;

import com.aristotle.admin.controller.beans.UserSessionObject;
import com.aristotle.admin.service.HttpSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginViewFilter implements Filter {

    @Autowired
    private HttpSessionUtil httpSessionUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserSessionObject userSessionObject = httpSessionUtil.getLoggedInUserSessionObject((HttpServletRequest) request);
        if (userSessionObject == null) {
            ((HttpServletResponse) response).sendRedirect("/loginView.html");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
