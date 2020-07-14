package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.config.domain.JWTToken;
import com.doctor.visit.domain.BusFeedback;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.repository.BusUserMapper;
import com.doctor.visit.security.AuthoritiesConstants;
import com.doctor.visit.security.jwt.TokenProvider;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.Des3Util;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
                if (null != busUser && !"0".equals(busUser.getStatus())) {
                    return ComResponse.fail("用户被限制");
                }
                boolean insert = (null == busUser);
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


    /**
     * 获取微信用户列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusUser>> listBusUser(BusUser bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(bus.getName())){
            criteria.andLike("name",bus.getName()+"%");
        }
        if(StringUtils.isNotBlank(bus.getWechatNickname())){
            criteria.andLike("wechatNickname",bus.getWechatNickname()+"%");
        }
        criteria.andEqualTo("isDel",Constants.EXIST);

        Page<BusUser> busList = (Page<BusUser>) busUserMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> insertOrUpdateUserPullToBlacklist(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusUser delRecord = new BusUser();
            delRecord.setStatus("1");
            delRecord.setId(Long.parseLong(id));
            int i = busUserMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 根据id删除医院
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> insertOrUpdateUserPushFromBlacklist(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusUser delRecord = new BusUser();
            delRecord.setStatus("0");
            delRecord.setId(Long.parseLong(id));
            int i = busUserMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
