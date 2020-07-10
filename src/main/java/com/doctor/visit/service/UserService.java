package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.config.domain.JWTToken;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.repository.BusUserMapper;
import com.doctor.visit.security.AuthoritiesConstants;
import com.doctor.visit.security.jwt.TokenProvider;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.Des3Util;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 前端的用户 业务层
 */
@Service
public class UserService {

    @Value("${my.wx.auth-code2session}")
    private String authCode2Session;

    private final CommonService commonService;
    private final TokenProvider tokenProvider;
    private final BusUserMapper busUserMapper;

    public UserService(CommonService commonService, TokenProvider tokenProvider, BusUserMapper busUserMapper) {
        this.commonService = commonService;
        this.tokenProvider = tokenProvider;
        this.busUserMapper = busUserMapper;
    }

    /**
     * 登录接口
     *
     * @param jsCode
     * @return
     */
    public ComResponse<BusUser> authenticate(String jsCode, HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isBlank(jsCode)) {
            return ComResponse.failBadRequest();
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .hostnameVerifier((s, sslSession) -> true)
            .build();
        authCode2Session = authCode2Session.replace("JSCODE", jsCode);
        Request request = new Request.Builder()
            .url(authCode2Session)
            .get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            if (StringUtils.isBlank(result)) {
                return ComResponse.fail();
            }
            Gson gson = new Gson();
            Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>() {
            }.getType());
            if (null == resultMap) {
                return ComResponse.fail();
            }
            //是否有openid
            if (Constants.WX_SUCCESS_CODE.equals(resultMap.get(Constants.WX_ERR_CODE))) {
                String openid = resultMap.get(Constants.WX_OPEN_ID);
                String sessionKey = resultMap.get(Constants.WX_SESSION_KEY);
                //查看数据库是否有该用户信息
                BusUser busUser = commonService.getBusUser(openid);
                boolean insert = null == busUser;
                if (insert) {
                    busUser = new BusUser();
                    busUser.setId(IDKeyUtil.generateId());
                }
                busUser.setWechatOpenid(openid);
                busUser.setWechatSessionKey(sessionKey);
                busUser.setCreateBy(busUser.getId());
                busUser.setCreateTime(new Date());
                busUser.setEditBy(busUser.getId());
                busUser.setEditTime(new Date());
                busUser.setLastLoginInfo(httpServletRequest.getHeader(Constants.USER_AGENT));
                if (insert) {
                    //新增
                    busUserMapper.insertSelective(busUser);
                } else {
                    //更新
                    busUserMapper.updateByPrimaryKeySelective(busUser);
                }
                busUser.setToken(Utils.createToken(busUser.getId()));
                return ComResponse.ok(busUser);
            } else {
                return ComResponse.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ComResponse.fail();

    }
}
