package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.BusEvaluateMapper;
import com.doctor.visit.repository.BusFeedbackMapper;
import com.doctor.visit.repository.BusUserMapper;
import com.doctor.visit.repository.JhiPersistentAuditEventMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.service.common.CommonService;
import com.doctor.visit.service.common.UploadService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * 我的业务层
 */
@Service
public class MineServiceImpl implements com.doctor.visit.service.MineService {

    private final CommonService commonService;
    private final UploadService uploadService;

    //
    private final BusEvaluateMapper busEvaluateMapper;
    private final BusUserMapper busUserMapper;
    private final BusFeedbackMapper busFeedbackMapper;
    private final JhiPersistentAuditEventMapper jhiPersistentAuditEventMapper;

    public MineServiceImpl(CommonService commonService, UploadService uploadService, BusEvaluateMapper busEvaluateMapper, BusUserMapper busUserMapper, BusFeedbackMapper busFeedbackMapper, JhiPersistentAuditEventMapper jhiPersistentAuditEventMapper) {
        this.commonService = commonService;
        this.uploadService = uploadService;
        this.busEvaluateMapper = busEvaluateMapper;
        this.busUserMapper = busUserMapper;
        this.busFeedbackMapper = busFeedbackMapper;
        this.jhiPersistentAuditEventMapper = jhiPersistentAuditEventMapper;
    }

    /**
     * 我的”界面，需要一个返回 当前收藏数、分享数、关注数的接口
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ComResponse findMineCount(BusUser bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }

        return ComResponse.ok(busUserMapper.findMineCount(busUser.getId()));

    }

    /**
     * 前台 - 新增或者更新评价
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    @Override
    public ComResponse<BusEvaluate> insertOrUpdateEvaluate(BusEvaluate bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditBy(busUser.getId());
        bus.setEditTime(new Date());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            //修改评价
            busEvaluateMapper.updateByPrimaryKeySelective(bus);
        } else {
            //新增评价
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateBy(busUser.getId());
            bus.setCreateTime(new Date());
            bus.setCreateName(busUser.getName());
            busEvaluateMapper.insertSelective(bus);
        }
        BusFile busFile = new BusFile();
        busFile.setBus("bus_evaluate");
        busFile.setBusId(bus.getId());
        busFile.setFileType(Constants.FILE_TYPE_IMG);
        uploadService.uploadFiles(busFile, request);

        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除评价
     *
     * @param ids
     * @return
     */
    @Override
    public ComResponse<StringBuilder> deleteEvaluate(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusEvaluate delRecord = new BusEvaluate();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busEvaluateMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }


    /**
     * 首页：微信总用户数，微信本月新增用户，
     *
     * @return
     */
    @Override
    public ComResponse<Map<String,Integer>> findStatisticsUser() {
        Integer allCount = busUserMapper.countAllUser(null);
        Integer thisMonthCount = busUserMapper.countAllUser("thisMonth");
        Integer thisMonthFeedbackCount = busFeedbackMapper.countFeedback("thisMonth");

        Map<String,Integer> countMap = Maps.newHashMap();
        countMap.put("all",allCount);
        countMap.put("thisMonth",thisMonthCount);
        countMap.put("thisMonthFeedback",thisMonthFeedbackCount);
        return ComResponse.ok(countMap);
    }


    /**
     * 后台 - 首页：获取最近登录状态
     *
     * @return
     */
    @Override
    public ComResponse<JhiPersistentAuditEvent> findLastLoginInfo() {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
        }
        JhiPersistentAuditEvent jhiPersistentAuditEvent = jhiPersistentAuditEventMapper.selectLastLoginInfo(usernameOptional.get());

        return ComResponse.ok(jhiPersistentAuditEvent);
    }

}
