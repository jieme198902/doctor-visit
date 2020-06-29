package com.doctor.visit.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }

    public static final String COMMA = ",";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 删除
     */
    public static final String DELETE = "0";
    /**
     * 存在
     */
    public static final String EXIST = "1";
    /**
     * api_base 开头，暂时不以api开头，先不走jwt验证
     */
    public static final String API_BASE_FRONT = "/doctor";



    public static final String API_BASE_SYS = "/api";
}
