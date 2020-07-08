package com.doctor.visit.security.ud;

import com.doctor.visit.config.Constants;
import com.doctor.visit.security.jwt.TokenProvider;
import com.doctor.visit.web.rest.errors.UnAuthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义jwt验证过滤器
 */
public class UDUserJWTFilter implements Filter {

    private final String excludeLogin;
    private final TokenProvider tokenProvider;

    public UDUserJWTFilter(String excludeLogin, TokenProvider tokenProvider) {
        this.excludeLogin = excludeLogin;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String[] exLoginAry = excludeLogin.split(Constants.COMMA);
        boolean check = true;
        if (exLoginAry.length > 0) {
            for (String url : exLoginAry) {
                if (httpServletRequest.getRequestURI().contains(url)) {
                    filterChain.doFilter(httpServletRequest, servletResponse);
                    check = false;
                    break;
                }
            }
        }
        if (check) {
            String authorization = httpServletRequest.getHeader(Constants.AUTHORIZATION_HEADER);
            if (StringUtils.isBlank(authorization)) {
                throw new UnAuthorizedException();
            } else {
                String jwt = resolveToken(httpServletRequest);
                if (StringUtils.isBlank(jwt) || !tokenProvider.validateToken(jwt)) {
                    throw new UnAuthorizedException();
                } else {
                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(httpServletRequest, servletResponse);
                }
            }
        }
    }

    /**
     * 获取Authorization
     *
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public void destroy() {
    }
}
