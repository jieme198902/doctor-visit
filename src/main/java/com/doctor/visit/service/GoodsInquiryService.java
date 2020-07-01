package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusGoodsInquiry;
import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusGoodsInquiryMapper;
import com.doctor.visit.repository.BusGoodsSpecificationMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 问诊的商品
 */
@Service
public class GoodsInquiryService {
    private final CommonService commonService;

    private final BusGoodsInquiryMapper busGoodsInquiryMapper;

    public GoodsInquiryService(CommonService commonService, BusGoodsInquiryMapper busGoodsInquiryMapper) {
        this.commonService = commonService;
        this.busGoodsInquiryMapper = busGoodsInquiryMapper;
    }

    /**
     * 获取问诊商品列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusGoodsInquiry>> listGoodsInquiry(BusGoodsInquiry bus, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        bus.setIsDel(Constants.EXIST);

        Page<BusGoodsInquiry> busList = (Page<BusGoodsInquiry>) busGoodsInquiryMapper.select(bus);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新问诊商品
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<BusGoodsInquiry> insertOrUpdateGoodsInquiry(BusGoodsInquiry bus, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            bus.setEditTime(new Date());
            bus.setEditBy(jhiUser.getId());
            bus.setEditName(jhiUser.getFirstName());
            if (null != bus.getId()) {
                busGoodsInquiryMapper.updateByPrimaryKeySelective(bus);
            } else {
                bus.setId(IDKeyUtil.generateId());
                bus.setCreateTime(new Date());
                bus.setCreateBy(jhiUser.getId());
                bus.setCreateName(jhiUser.getFirstName());
                busGoodsInquiryMapper.insertSelective(bus);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(bus);
    }


    /**
     * 根据id删除问诊商品
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteGoodsInquiry(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusGoodsInquiry delRecord = new BusGoodsInquiry();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busGoodsInquiryMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
