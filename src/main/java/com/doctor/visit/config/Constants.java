package com.doctor.visit.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * api_base 开头，暂时不以api开头，先不走jwt验证
     */
    public static final String API_BASE = "/doctor";
}
