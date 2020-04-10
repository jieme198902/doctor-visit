package com.doctor.visit.web.rest.errors;

import org.springframework.http.HttpStatus;

/**
 * 不能授权的请求
 */
public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}
