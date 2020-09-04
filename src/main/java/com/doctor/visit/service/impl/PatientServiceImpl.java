package com.doctor.visit.service.impl;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusHospital;
import com.doctor.visit.domain.BusPatient;
import com.doctor.visit.domain.BusUser;
import com.doctor.visit.domain.JhiUser;
import com.doctor.visit.repository.BusPatientMapper;
import com.doctor.visit.security.SecurityUtils;
import com.doctor.visit.service.CommonService;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 患者信息 业务层
 */
@Service
public class PatientServiceImpl {

    private final CommonService commonService;
    private final BusPatientMapper busPatientMapper;

    public PatientServiceImpl(CommonService commonService, BusPatientMapper busPatientMapper) {
        this.commonService = commonService;
        this.busPatientMapper = busPatientMapper;
    }

    /**
     * 前台 - 获取患者列表
     *
     * @param bus
     * @param pageable
     * @return
     */
    public ComResponse<List<BusPatient>> listPatient(BusPatient bus, Pageable pageable, HttpServletRequest request, boolean sys) throws Exception {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        Example example = new Example(BusPatient.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDel", Constants.EXIST);
        //患者名字
        if (StringUtils.isNotBlank(bus.getName())) {
            criteria.andLike("name", bus.getName() + "%");
        }
        if (!sys) {
            //创建者
            Long userId = Utils.getUserId(request);
            criteria.andEqualTo("createBy", userId);
        }

        Page<BusPatient> busList = (Page<BusPatient>) busPatientMapper.selectByExample(example);
        return ComResponse.ok(busList.getResult(), busList.getTotal());
    }

    /**
     * 前台 - 新增或者更新患者
     *
     * @param bus
     * @param request
     * @return
     */
    public ComResponse<BusPatient> insertOrUpdatePatient(BusPatient bus, HttpServletRequest request) throws Exception {
        Long userId = Utils.getUserId(request);
        bus.setCreateBy(userId);
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
            busPatientMapper.updateByPrimaryKeySelective(bus);
        } else {
            bus.setId(IDKeyUtil.generateId());
            bus.setCreateTime(new Date());
            bus.setCreateBy(busUser.getId());
            bus.setCreateName(busUser.getName());
            busPatientMapper.insertSelective(bus);
        }

        return ComResponse.ok(bus);
    }


    /**
     * 前台 - 根据id删除患者
     *
     * @param ids
     * @return
     */
    public ComResponse<StringBuilder> deletePatient(String ids) {
        String[] idsAry = ids.split(Constants.COMMA);
        StringBuilder delIds = new StringBuilder();
        for (String id : idsAry) {
            BusPatient delRecord = new BusPatient();
            delRecord.setIsDel(Constants.DELETE);
            delRecord.setId(Long.parseLong(id));
            int i = busPatientMapper.updateByPrimaryKeySelective(delRecord);
            if (1 == i) {
                delIds.append(id);
            }
        }
        return ComResponse.ok(delIds);
    }
}
