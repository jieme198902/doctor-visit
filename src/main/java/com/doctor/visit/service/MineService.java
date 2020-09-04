package com.doctor.visit.service;

import com.doctor.visit.domain.BusEvaluate;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.JhiPersistentAuditEvent;
import com.doctor.visit.web.rest.util.ComResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MineService {
    /**
     * 我的”界面，需要一个返回 当前收藏数、分享数、关注数的接口
     *
     * @param bus
     * @param request
     * @return
     * @throws Exception
     */
    ComResponse findMineCount(BusUser bus, HttpServletRequest request) throws Exception;

    /**
     * 前台 - 新增或者更新评价
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    ComResponse<BusEvaluate> insertOrUpdateEvaluate(BusEvaluate bus, HttpServletRequest request) throws Exception;

    /**
     * 根据id删除评价
     *
     * @param ids
     * @return
     */
    ComResponse<StringBuilder> deleteEvaluate(String ids);

    /**
     * 首页：微信总用户数，微信本月新增用户，
     *
     * @return
     */
    ComResponse<Map<String, Integer>> findStatisticsUser();

    /**
     * 后台 - 首页：获取最近登录状态
     *
     * @return
     */
    ComResponse<JhiPersistentAuditEvent> findLastLoginInfo();
}
