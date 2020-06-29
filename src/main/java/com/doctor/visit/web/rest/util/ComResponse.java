package com.doctor.visit.web.rest.util;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * response
 *
 * @param <T>
 */
public final class ComResponse<T> implements Serializable {
    private Integer status;
    private String message;
    private T data;
    private Long total;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ComResponse(T data, Integer status, String message, Long total) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.total = total;
    }

    /**
     * ok 200
     *
     * @param data
     * @param total
     * @return
     */
    public static <T> ComResponse<T> ok(T data, Long total) {
        return of(data, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), total);
    }

    /**
     * ok 200
     *
     * @param data
     * @return
     */
    public static <T> ComResponse<T> ok(T data) {
        return ok(data, 0L);
    }

    /**
     * fail 500
     *
     * @param <T>
     * @return
     */
    public static <T> ComResponse<T> fail() {
        return of(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 0L);
    }

    /**
     * fail 401
     *
     * @param <T>
     * @return
     */
    public static <T> ComResponse<T> failUnauthorized() {
        return of(null, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), 0L);
    }

    /**
     * fail 401
     *
     * @param <T>
     * @return
     */
    public static <T> ComResponse<T> failNotFound() {
        return of(null, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), 0L);
    }

    /**
     * all
     *
     * @param data
     * @param status
     * @param message
     * @param total
     * @param <T>
     * @return
     */
    public static <T> ComResponse<T> of(T data, int status, String message, Long total) {
        return new ComResponse<>(data, status, message, total);
    }

}
