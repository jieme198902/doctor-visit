package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusOrderInquiry;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.repository.BusOrderInquiryMapper;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 问诊订单
 */
@Service
public class OrderInquiryService {

    private final CommonService commonService;
    private final BusOrderInquiryMapper busOrderInquiryMapper;

    public OrderInquiryService(CommonService commonService, BusOrderInquiryMapper busOrderInquiryMapper) {
        this.commonService = commonService;
        this.busOrderInquiryMapper = busOrderInquiryMapper;
    }

    /**
     * 获取问诊订单列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusOrderInquiry>> listOrderInquiry(BusOrderInquiry bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);
        Page<BusOrderInquiry> busList = (Page<BusOrderInquiry>) busOrderInquiryMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }


    /**
     * 前端 - 新增或者更新问诊订单
     *
     * @param bus
     * @param request 这里需要处理文件
     * @return
     */
    public ComResponse<BusOrderInquiry> insertOrUpdateOrderInquiry(BusOrderInquiry bus, HttpServletRequest request) throws Exception {
        bus.setCreateBy(Utils.getUserId(request));
        if (null == bus.getCreateBy()) {
            return ComResponse.failBadRequest();
        }
        BusUser busUser = commonService.getBusUser(bus.getCreateBy());
        if (null == busUser) {
            return ComResponse.failUnauthorized();
        }
        bus.setEditTime(new Date());
        bus.setEditBy(busUser.getId());
        bus.setEditName(busUser.getName());
        if (null != bus.getId()) {
            busOrderInquiryMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateTime(new Date());
            bus.setCreateBy(busUser.getId());
            bus.setCreateName(busUser.getName());
            busOrderInquiryMapper.insertSelective(bus);
        }
        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除问诊订单
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteOrderInquiry(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusOrderInquiry delRecord = new BusOrderInquiry();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busOrderInquiryMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
