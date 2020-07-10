package com.doctor.visit.web.rest.util;

import com.doctor.visit.config.Constants;
import com.doctor.visit.web.rest.errors.UnAuthorizedException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class Utils {

    private Utils() {
    }

    /**
     * 获取token
     *
     * @param userId
     * @return
     */
    public static String createToken(Long userId) throws Exception {
        String tokenOrigin = userId + Constants.UNDERLINE + System.currentTimeMillis();
        return Des3Util.encode(tokenOrigin, Constants.des3Key);
    }

    /**
     * 通过token获取userId
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Long getUserId(String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            throw new UnAuthorizedException("token is null . ");
        }
        String idTime = Des3Util.decode(token, Constants.des3Key);
        if (StringUtils.isBlank(idTime)) {
            throw new UnAuthorizedException("token is error . ");
        }
        String idStr = idTime.split(Constants.UNDERLINE)[0];
        if (StringUtils.isBlank(idStr)) {
            throw new UnAuthorizedException("token is error . ");
        }
        return Long.parseLong(idStr);
    }


    /**
     * 从header中获取token
     * @param request
     * @return
     * @throws Exception
     */
    public static Long getUserId(HttpServletRequest request) throws Exception {
        String token = request.getHeader(Constants.TOKEN);
        if(StringUtils.isBlank(token)){
            throw new UnAuthorizedException("token is null . ");
        }
        return getUserId(token);
    }

}
