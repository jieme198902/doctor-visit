package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusDict;
import com.doctor.visit.domain.BusLog;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.dto.BusUserDto;
import com.doctor.visit.repository.BusDictMapper;
import com.doctor.visit.repository.BusLogMapper;
import com.doctor.visit.repository.BusUserMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 前端的用户 业务层
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final CommonService commonService;
    private final BusUserMapper busUserMapper;
    private final BusDictMapper busDictMapper;
    private final BusLogMapper busLogMapper;
    private Gson gson = new Gson();

    public UserService(CommonService commonService, BusUserMapper busUserMapper, BusDictMapper busDictMapper, BusLogMapper busLogMapper) {
        this.commonService = commonService;
        this.busUserMapper = busUserMapper;
        this.busDictMapper = busDictMapper;
        this.busLogMapper = busLogMapper;
    }

    /**
     * 登录接口
     *
     * @param jsCode
     * @return
     */
    public ComResponse<BusUserDto> authenticate(String jsCode, HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isBlank(jsCode)) {
            return ComResponse.failBadRequest();
        }
        logger.info("jsCode-->{}", jsCode);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .hostnameVerifier((s, sslSession) -> true)
            .build();
        BusDict wxDict = new BusDict();
        wxDict.setDicType("WXPZ");
        Map<String, String> wxConfig = Maps.newHashMap();
        busDictMapper.select(wxDict).forEach(dict -> wxConfig.put(dict.getDicName(), dict.getDicValue()));
        //https://api.weixin.qq.com/sns/jscode2session?appid=${my.wx.appid}&secret=${my.wx.secret}&js_code=JSCODE&grant_type=authorization_code
        String authCode2Session = wxConfig.get("wx_auth_code_2_session");
        if (StringUtils.isBlank(authCode2Session)) {
            return ComResponse.fail("微信小程序配置有问题，请联系管理员");
        }
        String wxAppid = wxConfig.get("wx_appid");
        if (StringUtils.isBlank(wxAppid)) {
            return ComResponse.fail("微信小程序配置有问题，请联系管理员");
        }
        String wxSecret = wxConfig.get("wx_secret");
        if (StringUtils.isBlank(wxSecret)) {
            return ComResponse.fail("微信小程序配置有问题，请联系管理员");
        }
        authCode2Session = authCode2Session.replace("JSCODE", jsCode).replace("${my.wx.appid}", wxAppid).replace("${my.wx.secret}", wxSecret);
        logger.debug(authCode2Session);
        Request request = new Request.Builder()
            .url(authCode2Session)
            .get().build();
        try {
            Map<String, Object> requestMap = Maps.newHashMap();
            requestMap.put("jsCode", jsCode);
            requestMap.put(Constants.USER_AGENT, httpServletRequest.getHeader(Constants.USER_AGENT));
            //
            BusLog loginLog = new BusLog();
            loginLog.setId(IDKeyUtil.generateId());
            loginLog.setCreateTime(new Date());
            loginLog.setModel("微信登录");
            loginLog.setOperation("登录");
            loginLog.setSys("0");
            loginLog.setRequest(gson.toJson(requestMap));
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            if (StringUtils.isBlank(result)) {
                //记录日志
                requestMap.put("result", "请求微信服务器超时");
                loginLog.setResponse(gson.toJson(requestMap));
                busLogMapper.insertSelective(loginLog);
                return ComResponse.fail("请求失败，请稍后重试");
            }
            loginLog.setResponse(result);
            logger.info("wx.response-->{}", result);
            Map<String, String> resultMap = gson.fromJson(result, new TypeToken<Map<String, String>>() {
            }.getType());
            if (null == resultMap) {
                //记录日志
                requestMap.put("result", "微信返回的数据解析失败");
                loginLog.setResponse(gson.toJson(requestMap));
                busLogMapper.insertSelective(loginLog);
                return ComResponse.fail("解析失败，请稍后重试");
            }

            // {"session_key":"cD40SWcXkPQzwktRUs9vzQ==","expires_in":7200,"openid":"o8Uf90A16CnNtcD5rQ983ZoFG-nk"}
            //是否有openid、sessionKey，如果有则算成功
            if (resultMap.containsKey(Constants.WX_OPEN_ID) && resultMap.containsKey(Constants.WX_SESSION_KEY)) {
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
                BusUserDto busUserDto = gson.fromJson(gson.toJson(busUser), BusUserDto.class);

                busUserDto.setToken(Utils.createToken(busUser.getId()));
                //记录日志
                loginLog.setCreateBy(busUser.getId());
                if (StringUtils.isBlank(busUser.getName())) {
                    loginLog.setCreateName(openid);
                } else {
                    loginLog.setCreateName(busUser.getName());
                }
                requestMap.put("user",busUserDto);
                loginLog.setResponse(gson.toJson(requestMap));
                busLogMapper.insertSelective(loginLog);

                return ComResponse.ok(busUserDto);
            } else {
                //记录日志
                loginLog.setResponse(gson.toJson(resultMap));
                busLogMapper.insertSelective(loginLog);
                String errCode = resultMap.get(Constants.WX_ERR_CODE);
                switch (errCode) {
                    case "-1":
                        return ComResponse.fail("系统繁忙，请稍候再试");
                    case "45011":
                        return ComResponse.fail("频率限制");
                    case "40029":
                        return ComResponse.fail("code 无效");
                    default:
                        return ComResponse.fail(result);
                }
            }
        } catch (Exception e) {
            logger.error("login.error-->{}",e.getMessage());
            return ComResponse.fail(e.getMessage());
        }
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
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        if (StringUtils.isNotBlank(bus.getWechatNickname())) {
            criteria.andLike("wechatNickname", bus.getWechatNickname() + "%");
        }
        criteria.andEqualTo("isDel", Constants.EXIST);

        Page<BusUser> busList = (Page<BusUser>) busUserMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 拉入黑名单
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
     * 根据 把用户从黑名单拉出来
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
