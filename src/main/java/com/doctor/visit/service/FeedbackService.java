package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.*;
import com.doctor.visit.repository.BusFeedbackMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 意见反馈
 */
@Service
public class FeedbackService {
    private final CommonService commonService;
    private final BusFeedbackMapper busFeedbackMapper;

    public FeedbackService(CommonService commonService, BusFeedbackMapper busFeedbackMapper) {
        this.commonService = commonService;
        this.busFeedbackMapper = busFeedbackMapper;
    }

    /**
     * 获取意见反馈列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusFeedback>> listFeedback(BusFeedback bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);
        Page<BusFeedback> busList = (Page<BusFeedback>) busFeedbackMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }


    /**
     * 前端 - 新增或者更新意见反馈
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    public ComResponse<BusFeedback> insertOrUpdateFeedback(BusFeedback bus, HttpServletRequest request) throws Exception {
        BusUser busUser = commonService.getBusUser(Utils.getUserId(request));
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            busFeedbackMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateTime(new Date());
            bus.setCreateBy(busUser.getId());
            bus.setCreateName(busUser.getName());
            busFeedbackMapper.insertSelective(bus);
        }
        return ComResponse.ok(bus);
    }

    /**
     * 插入或者更新意见反馈的状态
     *
     * @param bus
     * @return
     * @throws Exception
     */
    public ComResponse<BusFeedback> insertOrUpdateFeedbackState(BusFeedback bus) throws Exception {
        BusFeedback update = new BusFeedback();
        update.setId(bus.getId());
        update.setState(bus.getState());
        busFeedbackMapper.updateByPrimaryKeySelective(update);
        return ComResponse.ok(update);
    }


    /**
     * 根据id删除意见反馈
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteFeedback(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusFeedback delRecord = new BusFeedback();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busFeedbackMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
