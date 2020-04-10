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
    public static <T> ComResponse<T> OK(T data, Long total) {
        return ALL(data, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), total);
    }

    /**
     * ok 200
     *
     * @param data
     * @return
     */
    public static <T> ComResponse<T> OK(T data) {
        return OK(data, 0L);
    }

    /**
     * fail 500
     *
     * @param <T>
     * @return
     */
    public static <T> ComResponse<T> FAIL() {
        return ALL(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 0L);
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
    public static <T> ComResponse<T> ALL(T data, int status, String message, Long total) {
        return new ComResponse<>(data, status, message, total);
    }

}
