package com.doctor.visit.config.filter;

import com.doctor.visit.service.SysAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 查询用户访问的菜单及按钮，
 * 查看是否有权限
 */
public class AuthFilter implements Filter {

    private final SysAuthService sysAuthService;

    public AuthFilter(SysAuthService sysAuthService) {
        this.sysAuthService = sysAuthService;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (sysAuthService.hasPermission(request)) {
            filterChain.doFilter(request, servletResponse);
        } else {
            throw new RuntimeException("无权限访问。");
        }
    }

    @Override
    public void destroy() {

    }
}
