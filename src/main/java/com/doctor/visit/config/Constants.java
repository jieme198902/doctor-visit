package com.doctor.visit.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }

    public static final String COMMA = ",";
    public static final String UNDERLINE = "_";
    public static final String TOKEN = "token";

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String USER_AGENT = "User-Agent";

    public static final String WX_SUCCESS_CODE = "0";
    public static final String WX_OPEN_ID = "openid";
    public static final String WX_SESSION_KEY = "session_key";
    public static final String WX_ERR_CODE = "errcode";

    public static final String des3Key = "201920200203abcdqaz12017";

    /**
     * 删除
     */
    public static final String DELETE = "1";
    /**
     * 存在
     */
    public static final String EXIST = "0";
    /**
     * 前端的微信端 可以不登录
     */
    public static final String API_BASE_FRONT = "/front";

    /**
     * 后台的都需要登录
     */
    public static final String API_BASE_SYS = "/api";
}
