package com.doctor.visit.web.rest.util;

import org.springframework.http.HttpStatus;

/**
 * response
 *
 * @param <T>
 */
public final class ComResponse<T> {


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
     * ok
     *
     * @param body
     * @param total
     * @return
     */
    public static <T> ComResponse<T> OK(T body, Long total) {
        if (null == total) {
            total = 0L;
        }
        return new ComResponse<>(body, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), total);
    }

    /**
     * ok
     *
     * @param body
     * @return
     */
    public static <T> ComResponse<T> OK(T body) {
        return OK(body, 0L);
    }


}
