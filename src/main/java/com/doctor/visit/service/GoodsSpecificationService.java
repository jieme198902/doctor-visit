package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusGoodsSpecification;
import com.doctor.visit.domain.JhiUser;
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
 * 商品规格 逻辑
 */
@Service
public class GoodsSpecificationService {

    private final CommonService commonService;

    private final BusGoodsSpecificationMapper busGoodsSpecificationMapper;

    public GoodsSpecificationService(CommonService commonService, BusGoodsSpecificationMapper busGoodsSpecificationMapper) {
        this.commonService = commonService;
        this.busGoodsSpecificationMapper = busGoodsSpecificationMapper;
    }

    /**
     * 前台 - 获取商品规格列表
     *
     * @param busGoodsSpecification
     * @param pageable
     * @return
     */
    public ComResponse<List<BusGoodsSpecification>> listGoodsSpecification(BusGoodsSpecification busGoodsSpecification, Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        busGoodsSpecification.setIsDel(Constants.EXIST);

        Page<BusGoodsSpecification> busList = (Page<BusGoodsSpecification>) busGoodsSpecificationMapper.select(busGoodsSpecification);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 新增或者更新商品规格
     *
     * @param busGoodsSpecification
     * @param request
     * @return
     */
    public ComResponse<BusGoodsSpecification> insertOrUpdateGoodsSpecification(BusGoodsSpecification busGoodsSpecification, HttpServletRequest request) {
        Optional<String> usernameOptional = SecurityUtils.getCurrentUserLogin();
        if (usernameOptional.isPresent()) {
            JhiUser jhiUser = commonService.getJhiUser(usernameOptional.get());
            if (null == jhiUser) {
                return ComResponse.failNotFound();
            }
            busGoodsSpecification.setEditTime(new Date());
            busGoodsSpecification.setEditBy(jhiUser.getId());
            busGoodsSpecification.setEditName(jhiUser.getFirstName());
            if (null != busGoodsSpecification.getId()) {
                busGoodsSpecificationMapper.updateByPrimaryKeySelective(busGoodsSpecification);
            } else {
                busGoodsSpecification.setId(IDKeyUtil.generateId());
                busGoodsSpecification.setCreateTime(new Date());
                busGoodsSpecification.setCreateBy(jhiUser.getId());
                busGoodsSpecification.setCreateName(jhiUser.getFirstName());
                busGoodsSpecificationMapper.insertSelective(busGoodsSpecification);
            }
        } else {
            return ComResponse.failUnauthorized();
        }
        return ComResponse.ok(busGoodsSpecification);
    }


    /**
     * 根据id删除商品规格
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deleteGoodsSpecification(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusGoodsSpecification delRecord = new BusGoodsSpecification();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busGoodsSpecificationMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }

}
