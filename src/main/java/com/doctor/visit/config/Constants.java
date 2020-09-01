package com.doctor.visit.config;

import net.jodah.expiringmap.ExpiringMap;

import java.util.List;
import java.util.Map;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }

    /**
     * 行政区划
     */
    public static final ExpiringMap<String, List<Map<String,Object>>> areaMap = ExpiringMap.builder().variableExpiration().build();
    /**
     * 验证码
     */
    public static final ExpiringMap<String, String> codeMap = ExpiringMap.builder().variableExpiration().build();


    public static final String COMMA = ",";
    public static final String UNDERLINE = "_";
    public static final String MIDDLE_LINE = "-";
    public static final String POINT=".";
    public static final String SLASH="/";
    public static final String SPACE = "";

    public static final String FILE_TYPE_IMG = "0";

    public static final String TOKEN = "token";

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String USER_AGENT = "user-agent";

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
